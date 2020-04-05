package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.BinaryFunctions.Pow;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.SpecialFunctions.Variable;

public class Multiply extends CommutativeFunction{
	public Multiply(Function... functions) {
		super(functions);
		identityValue = 1;
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 1;
		for (Function f : functions) accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" * ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			Function[] toMultiply = new Function[functions.length];
			for (int j = 0; j < functions.length; j++) {
				if (j == i) toMultiply[j] = functions[j].getDerivative(varID);
				else toMultiply[j] = functions[j];
			}
			toAdd[i] = new Multiply(toMultiply);
		}

		return new Add(toAdd);
	}

	public Multiply clone() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].clone();
		return new Multiply(toMultiply);
	}


	public Function simplify() {
		Multiply init = simplifyInternal();
		if (init.isTimesZero())
			return new Constant((0));
		else
			return init.simplifyOneElement();
	}

	public Multiply simplifyInternal() {
		return ((Multiply) super.simplifyInternal()).addExponents();
	}

	@Override
	protected Multiply simplifyElements() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Multiply(toMultiply);
	}

	@Override
	protected Multiply simplifyIdentity() {
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).constant == 1) {
					return (new Multiply(ArrLib.removeFunctionAt(functions, i))).simplifyIdentity();
				}
			}
		}
		return this;
	}

	protected Multiply simplifyPull() {
		for (int i = 0; i < functions.length; i++) {
			if (this.getClass().equals(functions[i].getClass())) {
				return (new Multiply(ArrLib.pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i))).simplifyInternal();
			}
		}
		return this;
	}

	@Override
	protected Multiply simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).constant * ((Constant) functions[j]).constant);
					Function[] toMultiply = ArrLib.deepClone(functions);
					toMultiply[i] = c;
					toMultiply = ArrLib.removeFunctionAt(toMultiply, j);
					return (new Multiply(toMultiply)).simplifyConstants();
				}
			}
		}
		return this;
	}

	protected boolean isTimesZero() {
		for (Function function : functions) {
			if (function instanceof Constant) {
				if (((Constant) function).constant == 0) {
					return true;
				}
			}
		}
		return false;
	}

	protected Multiply addExponents() {
		Function[] simplifiedTerms = ArrLib.deepClone(functions);
		for (int a = 0; a < simplifiedTerms.length; a++) {
			if (simplifiedTerms[a] instanceof Variable)
				simplifiedTerms[a] = new Pow(simplifiedTerms[a], new Constant(1));
		}
		for (int i = 1; i < simplifiedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (simplifiedTerms[i] instanceof Pow && simplifiedTerms[j] instanceof Pow) {
					if (((Pow) simplifiedTerms[i]).getFunction2().equals(((Pow) simplifiedTerms[j]).getFunction2())) {
						Pow combinedExponents = new Pow(new Add(((Pow) simplifiedTerms[i]).getFunction1(), ((Pow) simplifiedTerms[j]).getFunction1()), ((Pow) simplifiedTerms[i]).getFunction2());
						simplifiedTerms[j] = combinedExponents;
						simplifiedTerms = ArrLib.removeFunctionAt(simplifiedTerms, i);
						return (new Multiply(simplifiedTerms)).simplifyInternal();
					}
				}
			}
		}
		return clone();
	}

	public int compareTo(Function f) {
		return 0;
	}
}
