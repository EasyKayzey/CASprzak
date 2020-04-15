package functions.commutative;

import tools.ArrLib;
import core.Settings;
import functions.Function;
import functions.special.Constant;

public class Add extends CommutativeFunction {
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

	/**
	 * Returns a String representation of the Function
	 *
	 * @return String representation of the Function
	 */
	public String toString() {
		if (functions.length < 1)
			return "(empty sum)";
		StringBuilder string = new StringBuilder("(");
		for (int i = functions.length - 1; i >= 1; i--) {
			string.append(functions[i].toString());
			string.append(" + ");
		}
		string.append(functions[0].toString());
		string.append(")");
		return string.toString();
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
		Add current = (Add) super.simplifyInternal();
		current = current.combineLikeTerms();
		return current;
	}


	@Override
	public Add simplifyElements() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++)
			toAdd[i] = functions[i].simplify();
		return new Add(toAdd);
	}

	public Add simplifyConstants() {
		for (int i = 1; i < functions.length; i++) {
			for (int j = 0; j < i; j++) {
				if (functions[i] instanceof Constant first && functions[j] instanceof Constant second) {
					Function[] toAdd = ArrLib.deepClone(functions);
					toAdd[i] = new Constant(first.constant + second.constant);
					toAdd = ArrLib.removeFunctionAt(toAdd, j);
					return (new Add(toAdd)).simplifyConstants();
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public Add combineLikeTerms() {
		Function[] combinedTerms = ArrLib.deepClone(functions);
		for (int a = 0; a < combinedTerms.length; a++) {
			if (!(combinedTerms[a] instanceof Multiply && ((Multiply) combinedTerms[a]).getFunctions()[0] instanceof Constant))
				combinedTerms[a] = new Multiply(new Constant(1), combinedTerms[a]).simplifyPull();
		}
		for (int i = 1; i < combinedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (combinedTerms[i] instanceof Multiply first && combinedTerms[j] instanceof Multiply second) {
					if (ArrLib.deepEquals(first.getFunctions(), second.getFunctions(), 1)) {
						combinedTerms[j] = new Multiply(new Add(first.getFunctions()[0], second.getFunctions()[0]), new Multiply(ArrLib.removeFunctionAt(first.getFunctions(), 0)));
						combinedTerms = ArrLib.removeFunctionAt(combinedTerms, i);
						return (new Add(combinedTerms)).simplifyInternal();
					}
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public CommutativeFunction me(Function... functions) {
		return new Add(functions);
	}
}
