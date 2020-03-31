package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.SpecialFunctions.Variable;

public class Add extends CommutativeFunction{
	public Add(Function... functions) {
		super(functions);
		identityValue = 0;
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
					Function[] toAdd = ArrLib.deepClone(functions);
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
		Function[] combinedTerms = ArrLib.deepClone(functions);
		for (int a = 0; a < functions.length; a++) {
			if (functions[a] instanceof Variable) functions[a] = new Multiply(new Constant(1), functions[a]);
		}
		for (int i = 1; i < functions.length; i++) {
			for (int j = 0; j < i; j++) {
				if (functions[i] instanceof Multiply && functions[j] instanceof Multiply) {
					Multiply mult1 = new Multiply(ArrLib.removeFunctionAt(((Multiply)functions[i]).getFunctions(), 0));
					Multiply mult2 = new Multiply(ArrLib.removeFunctionAt(((Multiply)functions[j]).getFunctions(), 0));
					if (mult1.equals(mult2)){
						Multiply multCombined = new Multiply(new Add(((Multiply)functions[i]).getFunctions()[0], ((Multiply)functions[j]).getFunctions()[0]), mult1);
						combinedTerms[j] = multCombined;
						combinedTerms = ArrLib.removeFunctionAt(combinedTerms, i);
					}
				}
			}
		}
		return new Add(combinedTerms);
	}

	public int compareTo(Function f) {
		return 0;
	}
}
