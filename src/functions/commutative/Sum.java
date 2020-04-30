package functions.commutative;

import config.Settings;
import functions.Function;
import functions.special.Constant;
import tools.FunctionTools;

import java.util.Map;

public class Sum extends CommutativeFunction {
	/**
	 * Constructs a new Add
	 * @param functions The terms being added together
	 */
	public Sum(Function... functions) {
		super(functions);
		identityValue = 0;
		operation = Double::sum;
	}

	public double evaluate(Map<Character, Double> variableValues) {
		double accumulator = identityValue;
		for (Function f : functions)
			accumulator += f.evaluate(variableValues);
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
		return new Sum(toAdd);
	}

	public Sum clone() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].clone();
		return new Sum(toAdd);
	}


	public Sum simplifyInternal() {
		Sum current = (Sum) super.simplifyInternal();
		current = current.combineLikeTerms();
		return current;
	}


	@Override
	public Sum simplifyElements() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++)
			toAdd[i] = functions[i].simplify();
		return new Sum(toAdd);
	}

	/**
	 * Returns a {@link Sum} where like terms are added together. Example: {@code 2x+x=3x}
	 * @return a {@link Sum} where like terms are added together
	 */
	public Sum combineLikeTerms() {
		Function[] combinedTerms = FunctionTools.deepClone(functions);
		for (int a = 0; a < combinedTerms.length; a++) {
			if (!(combinedTerms[a] instanceof Product && ((Product) combinedTerms[a]).getFunctions()[0] instanceof Constant))
				combinedTerms[a] = new Product(new Constant(1), combinedTerms[a]).simplifyPull();
		}
		for (int i = 1; i < combinedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (combinedTerms[i] instanceof Product first && combinedTerms[j] instanceof Product second) {
					if (FunctionTools.deepEquals(first.getFunctions(), second.getFunctions(), 1)) {
						combinedTerms[j] = new Product(new Sum(first.getFunctions()[0], second.getFunctions()[0]), new Product(FunctionTools.removeFunctionAt(first.getFunctions(), 0)));
						combinedTerms = FunctionTools.removeFunctionAt(combinedTerms, i);
						return (new Sum(combinedTerms)).simplifyInternal();
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
		return new Sum(functions);
	}
}
