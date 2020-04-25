package functions.commutative;

import core.Settings;
import functions.Function;
import functions.special.Constant;
import tools.FunctionTools;

import java.util.Map;

public class Add extends CommutativeFunction {
	/**
	 * Constructs a new Add
	 * @param functions The terms being added together
	 */
	public Add(Function... functions) {
		super(functions);
		identityValue = 0;
	}

	public double oldEvaluate(Map<Character, Double> variableValues) {
		double accumulator = identityValue;
		for (Function f : functions)
			accumulator += f.oldEvaluate(variableValues);
		return accumulator;
	}


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
	public Function getDerivative(char varID) {
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
					Function[] toAdd = FunctionTools.deepClone(functions);
					toAdd[i] = new Constant(first.constant + second.constant);
					toAdd = FunctionTools.removeFunctionAt(toAdd, j);
					return (new Add(toAdd)).simplifyConstants();
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	/**
	 * Returns a {@link Add} where like terms are added together. Example: {@code 2x+x=3x}
	 * @return a {@link Add} where like terms are added together
	 */
	public Add combineLikeTerms() {
		Function[] combinedTerms = FunctionTools.deepClone(functions);
		for (int a = 0; a < combinedTerms.length; a++) {
			if (!(combinedTerms[a] instanceof Multiply && ((Multiply) combinedTerms[a]).getFunctions()[0] instanceof Constant))
				combinedTerms[a] = new Multiply(new Constant(1), combinedTerms[a]).simplifyPull();
		}
		for (int i = 1; i < combinedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (combinedTerms[i] instanceof Multiply first && combinedTerms[j] instanceof Multiply second) {
					if (FunctionTools.deepEquals(first.getFunctions(), second.getFunctions(), 1)) {
						combinedTerms[j] = new Multiply(new Add(first.getFunctions()[0], second.getFunctions()[0]), new Multiply(FunctionTools.removeFunctionAt(first.getFunctions(), 0)));
						combinedTerms = FunctionTools.removeFunctionAt(combinedTerms, i);
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
