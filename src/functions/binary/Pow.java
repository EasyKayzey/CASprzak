package functions.binary;

import config.Settings;
import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
import tools.DefaultFunctions;
import tools.MiscTools;

import java.util.Arrays;
import java.util.Map;

public class Pow extends BinaryFunction {
	/**
	 * Constructs a new Pow
	 * @param exponent The exponent of the exponent
	 * @param base The base of the exponent
	 */
	public Pow(GeneralFunction exponent, GeneralFunction base) {
		super(exponent, base);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		if (function1 instanceof Constant exponent) //TODO should this use doesntContainConstant like in StageOne
			return new Product(new Constant(exponent.constant), new Pow(new Constant(exponent.constant - 1), function2), function2.getSimplifiedDerivative(varID));
		else
			return new Product(new Pow(function1, function2), new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2)), new Product(new Product(function1, function2.getSimplifiedDerivative(varID)), new Pow(DefaultFunctions.NEGATIVE_ONE, function2))));
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}

	public GeneralFunction clone() {
		return new Pow(function1.clone(), function2.clone());
	}

	public GeneralFunction simplify() {
		Pow currentPow = (new Pow(function1.simplify(), function2.simplify()));
		currentPow = currentPow.multiplyExponents();
		GeneralFunction current = currentPow.simplifyObviousExponentsAndFOC();
		if (current instanceof Pow pow && pow.function2 instanceof Product && Settings.distributeExponents)
			current = pow.distributeExponents();
		return current;
	}

	/**
	 * Returns a {@link GeneralFunction} where obvious exponents (ex: {@code (x+1)^1 or (x-1)^0}) have been simplified and {@link Constant} bases and {@link Constant} exponents (ex: {@code 2^7}) are simplified
	 * @return a {@link GeneralFunction} where obvious exponents and Functions of Constants are simplified
	 */
	public GeneralFunction simplifyObviousExponentsAndFOC() { //FOC means Functions of Constants
		if (function1 instanceof Constant constant) {
			if (constant.constant == 0)
				return new Constant(1);
			if (constant.constant == 1)
				return function2.simplify();
			if (Settings.simplifyFunctionsOfConstants && function2 instanceof Constant)
				return new Constant(this.evaluate(null));
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	/**
	 * Returns a {@link Pow} where an exponent to an exponent has been simplified. Example: {@code (x^2)^3 = x^6}
	 * @return a {@link Pow} where the exponents are multiplied
	 */
	public Pow multiplyExponents() {
		if (function2 instanceof Pow base)
			return new Pow(new Product(base.function1, function1).simplify(), base.function2);
		if (Settings.trustImmutability)
			return this;
		else
			return (Pow) clone();
	}

	/**
	 * Returns a {@link Product} where the exponent is on each term. Example: {@code (xy)^2 = (x^2)(y^2) }
	 * @return a {@link Product} with the exponent distributed
	 */
	public Product distributeExponents() {
		return new Product(distributeExponentsArray());
	}

	private GeneralFunction[] distributeExponentsArray() {
		if (function2 instanceof Product product) {
			GeneralFunction[] oldFunctions = product.getFunctions();
			GeneralFunction[] toMultiply = new GeneralFunction[oldFunctions.length];
			for (int i = 0; i < toMultiply.length; i++) {
				toMultiply[i] = new Pow(function1, oldFunctions[i]).simplify();
			}
			return toMultiply;
		} else {
			throw new IllegalCallerException("Method should not be called if base is not a Multiply");
		}
	}

	/**
	 * Given a Pow, checks if the exponent is a positive integer then unwraps it into a multiply
	 * @return a new unwrapped GeneralFunction
	 */
	public GeneralFunction unwrapIntegerPowerSafe() {
		if (function1 instanceof Constant constant && constant.constant >= 0) {
			try {
				return unwrapIntegerPower();
			} catch (IllegalArgumentException ignored) {
				// Do nothing
			}
		}
		if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	/**
	 * Given a Pow with positive integer exponent, unwraps it into a multiply
	 * @return a new unwrapped Multiply
	 * @throws RuntimeException if the exponent is not a positive integer
	 */
	public Product unwrapIntegerPower() throws RuntimeException {
		int intConstant = MiscTools.toInteger(((Constant) function1).constant);
		GeneralFunction[] toMultiply = new GeneralFunction[intConstant];
		Arrays.fill(toMultiply, function2);
		return new Product(toMultiply);
	}


	public BinaryFunction me(GeneralFunction function1, GeneralFunction function2) {
		return new Pow(function1, function2);
	}

	public GeneralFunction toSpecialCase() {
		return new Exp(new Product(new Ln(function2), function1));
	}


	@Override
	public String toString() {
		return "(" + function2 + "^" + function1 + ")";
	}
}
