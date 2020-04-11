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

	public double evaluate(double... variableValues) {
		double accumulator = identityValue;
		for (Function f : functions)
			accumulator += f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		if (functions.length < 1)
			return "(empty sum)";
		StringBuilder temp = new StringBuilder("(");
		for (int i = functions.length - 1; i >= 1; i--) {
			temp.append(functions[i].toString());
			temp.append(" + ");
		}
		temp.append(functions[0].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) {
			toAdd[i] = functions[i].getSimplifiedDerivative(varID);
		}
		return new Add(toAdd);
	}

	public Add clone() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].clone();
		return new Add(toAdd);
	}


	public Add simplifyInternal() {
		return ((Add) super.simplifyInternal()).combineLikeTerms();
	}


	@Override
	public Add simplifyElements() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].simplify();
		return new Add(toAdd);
	}

	@Override
	public Add simplifyIdentity() {
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).constant == 0) {
					return (new Add(ArrLib.removeFunctionAt(functions, i))).simplifyIdentity();
				}
			}
		}
		return this;
	}

	public Add simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).constant + ((Constant) functions[j]).constant);
					Function[] toAdd = ArrLib.deepClone(functions);
					toAdd[i] = c;
					toAdd = ArrLib.removeFunctionAt(toAdd, j);
					return (new Add(toAdd)).simplifyConstants();
				}
			}
		}
		return this;
	}

	public Add simplifyPull() {
		for (int i = 0; i < functions.length; i++) {
			if (this.getClass().equals(functions[i].getClass())) {
				return (new Add(ArrLib.pullUp(functions, ((CommutativeFunction) functions[i]).getFunctions(), i))).simplifyInternal();
			}
		}
		return this;
	}

	public Add combineLikeTerms() {
		Function[] combinedTerms = ArrLib.deepClone(functions);
		for (int a = 0; a < combinedTerms.length; a++) {
			if (combinedTerms[a] instanceof Variable)  combinedTerms[a] = new Multiply(new Constant(1), combinedTerms[a]);
		}
		for (int i = 1; i < combinedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (combinedTerms[i] instanceof Multiply && combinedTerms[j] instanceof Multiply) {
					Multiply mult1 = new Multiply(ArrLib.removeFunctionAt(((Multiply)combinedTerms[i]).getFunctions(), 0));
					Multiply mult2 = new Multiply(ArrLib.removeFunctionAt(((Multiply)combinedTerms[j]).getFunctions(), 0));
					if (mult1.equals(mult2)){
						Multiply multCombined = new Multiply(new Add(((Multiply)combinedTerms[i]).getFunctions()[0], ((Multiply)combinedTerms[j]).getFunctions()[0]), mult1);
						combinedTerms[j] = multCombined;
						combinedTerms = ArrLib.removeFunctionAt(combinedTerms, i);
						return (new Add(combinedTerms)).simplifyInternal();
					}
				}
			}
		}
		return clone();
	}

	public CommutativeFunction me(Function... functions) {
		return new Add(functions);
	}

	public int compareTo(Function f) {
		return 0;
	}
}
