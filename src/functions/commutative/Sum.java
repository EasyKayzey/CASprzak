package functions.commutative;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import tools.DefaultFunctions;
import tools.ArrayTools;

import java.util.Map;

public class Sum extends CommutativeFunction {
	/**
	 * Constructs a new Add
	 * @param functions The terms being added together
	 */
	public Sum(GeneralFunction... functions) {
		super(functions);
		identityValue = 0;
		operation = Double::sum;
	}

	public double evaluate(Map<Character, Double> variableValues) {
		double accumulator = identityValue;
		for (GeneralFunction f : functions)
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
	public GeneralFunction getDerivative(char varID) {
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++) {
			toAdd[i] = functions[i].getSimplifiedDerivative(varID);
		}
		return new Sum(toAdd);
	}

	public Sum clone() {
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];
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
		GeneralFunction[] toAdd = new GeneralFunction[functions.length];
		for (int i = 0; i < functions.length; i++)
			toAdd[i] = functions[i].simplify();
		return new Sum(toAdd);
	}

	/**
	 * Returns a {@link Sum} where like terms are added together. Example: {@code 2x+x=3x}
	 * @return a {@link Sum} where like terms are added together
	 */
	public Sum combineLikeTerms() {
		GeneralFunction[] combinedTerms = ArrayTools.deepClone(functions);
		for (int a = 0; a < combinedTerms.length; a++)
			if (!(combinedTerms[a] instanceof Product product && product.getFunctions()[0] instanceof Constant))
				combinedTerms[a] = new Product(DefaultFunctions.ONE, combinedTerms[a]).simplifyPull();

		for (int i = 1; i < combinedTerms.length; i++) {
			for (int j = 0; j < i; j++) {
				if (combinedTerms[i] instanceof Product first && combinedTerms[j] instanceof Product second) {
					GeneralFunction[] firstFunctions = first.getFunctions();
					GeneralFunction[] secondFunctions = second.getFunctions();
					if (!((firstFunctions[0] instanceof Constant) && (secondFunctions[0] instanceof Constant)))
						throw new IllegalStateException("Constants should always be first in a Multiply.");
					if (ArrayTools.deepEqualsExcluding(firstFunctions, secondFunctions, 0)) {
						combinedTerms[j] = new Product(new Sum(firstFunctions[0], secondFunctions[0]), new Product(ArrayTools.removeFunctionAt(firstFunctions, 0)));
						combinedTerms = ArrayTools.removeFunctionAt(combinedTerms, i);
						return (new Sum(combinedTerms)).simplifyInternal();
					}
				} else {
					throw new IllegalStateException("All Functions should have been converted to products");
				}
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	public CommutativeFunction me(GeneralFunction... functions) {
		return new Sum(functions);
	}
}
