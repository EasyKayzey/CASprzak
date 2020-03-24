package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.SpecialFunctions.Variable;

public class Add extends CommutativeFunction{
	double identity = 0;

	public Add(Function[] functions) {
		super(functions);
	}

	public Add(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 0;
		for (Function f : functions) accumulator += f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" + ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) {
			toAdd[i] = functions[i].getDerivative(varID);
		}
		return new Add(toAdd);
	}

	public Function clone() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].clone();
		return new Add(toAdd);
	}

	public Function simplify() {
		return super.simplifyInternal().simplifyOneElement();
	}


	@Override
	protected Add simplifyElements() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].simplify();
		return new Add(toAdd);
	}

	@Override
	protected Add simplifyIdentity() {
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).getConstant() == 0) {
					return (new Add(ArrLib.removeFunctionAt(functions, i))).simplifyIdentity();
				}
			}
		}
		return this;
	}

	protected Add simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).getConstant() + ((Constant) functions[j]).getConstant());
					Function[] toAdd = functions.clone();
					toAdd[i] = c;
					toAdd = ArrLib.removeFunctionAt(toAdd, j);
					return (new Add(toAdd)).simplifyConstants();
				}
			}
		}
		return this;
	}

	protected Function simplifyOneElement() {
		if (functions.length == 1) return functions[0].simplify();
		return this;
	}

	protected Add combineLikeTerms() {
		for (Function i: functions) {
			if (i instanceof Variable) i = new Multiply(new Constant(1), i);
		}
		for (int i = 1; i < functions.length; i++) {
			for (int j = 0; j < i; j++) {
				if (functions[i] instanceof Multiply && functions[j] instanceof Multiply) {
					//first, make sure it's sorted in the correct order (use .simplify)
					//TODO: sort things
					//make a NEW multiply with everything besides the first element (use Arrlib)
					//then check if the first one .equals the second one
					//if so, make a new multiply with the sum of the first elements, and then the second element of one of them (they are equal)

				}
			}
		}
		return this;
	}

	public int compareTo(Function f) {
		return 0;
	}
}
