package show.ezkz.casprzak.core.functions.binary;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Exp;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;
import show.ezkz.casprzak.core.output.OutputBinary;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.ParsingTools;
import show.ezkz.casprzak.core.tools.VariableTools;
import show.ezkz.casprzak.core.tools.helperclasses.LogInterface;
import show.ezkz.casprzak.core.tools.helperclasses.PowInterface;

import java.util.Arrays;
import java.util.Map;

import static show.ezkz.casprzak.core.output.ToStringManager.maybeParenthesize;

public class Pow extends BinaryFunction implements PowInterface {
	/**
	 * Constructs a new {@link Pow}
	 * @param exponent The exponent
	 * @param base The base
	 */
	public Pow(GeneralFunction exponent, GeneralFunction base) {
		super(exponent, base);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		if (function1 instanceof Constant exponent)
			return new Product(new Constant(exponent.constant), new Pow(new Constant(exponent.constant - 1), function2), function2.getSimplifiedDerivative(varID));
		else if (VariableTools.doesNotContainsVariable(function1, varID))
			return new Product(function1, new Pow(new Sum(function1, DefaultFunctions.NEGATIVE_ONE), function2), function2.getSimplifiedDerivative(varID));
		else
			return new Product(new Pow(function1, function2), new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2)), new Product(new Product(function1, function2.getSimplifiedDerivative(varID)), new Pow(DefaultFunctions.NEGATIVE_ONE, function2))));
	}

	public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
		return new Pow(function1, function2);
	}

	public Pow clone() {
		return new Pow(function1.clone(), function2.clone());
	}

	public GeneralFunction toSpecialCase() {
		return new Exp(new Product(new Ln(function2), function1));
	}

	@Override
	public String toString() {
		return "(" + function2 + "^" + function1 + ")";
	}

	public GeneralFunction simplify(SimplificationSettings settings) {
		Pow currentPow = new Pow(function1.simplify(settings), function2.simplify(settings));
		currentPow = currentPow.multiplyExponents(settings);
		GeneralFunction current = currentPow.simplifyObviousExponentsAndFOC(settings);
		if (settings.simplifyInverses && current instanceof Pow pow)
			current = pow.simplifyLogsOfSameBase(settings);
		if (settings.distributeExponentsOverMultiplication && current instanceof Pow pow && pow.function2 instanceof Product)
			current = pow.distributeExponents(settings);
		if (settings.unwrapIntegerPowers && current instanceof Pow pow)
			return pow.unwrapIntegerPowerSafe(settings);
		return current;
	}

	/**
	 * Returns a {@link GeneralFunction} where obvious exponents (ex: {@code (x+1)^1 or (x-1)^0}) have been simplified and functions of constants (ex: {@code 2^7}) are simplified
	 * @return a {@link GeneralFunction} where obvious exponents and functions of constants are simplified
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public GeneralFunction simplifyObviousExponentsAndFOC(SimplificationSettings settings) {
		if (function1 instanceof Constant constant)
			if (constant.constant == 0)
				return DefaultFunctions.ONE;
			else if (constant.constant == 1)
				return function2.simplify(settings);
		if (settings.simplifyFunctionsOfConstants)
			return simplifyFOC(settings);
		else
			return this;
	}

	@Override
	public GeneralFunction simplifyFOC(SimplificationSettings settings) {
		if (function1 instanceof Constant constant1 && function2 instanceof Constant constant2)
			if (settings.simplifyFunctionsOfSpecialConstants || (!constant1.isSpecial() && !constant2.isSpecial()))
				return new Constant(this.evaluate()).simplify(settings);
		return this;
	}

	/**
	 * Simplifies instances of a power raised to a power. Example: {@code (x^2)^3 = x^6}
	 * @return a {@link Pow} where the exponents are multiplied
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public Pow multiplyExponents(SimplificationSettings settings) {
		if (function2 instanceof Pow base)
			return new Pow(new Product(base.function1, function1).simplify(settings), base.function2);
		else
			return this;
	}

	/**
	 * Returns a {@link Product} where the exponent is distributed to each term. Example: {@code (xy)^2 = (x^2)(y^2) }
	 * @return a {@link Product} with the exponent distributed
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public Product distributeExponents(SimplificationSettings settings) {
		if (function2 instanceof Product product) {
			GeneralFunction[] oldFunctions = product.getFunctions();
			GeneralFunction[] toMultiply = new GeneralFunction[oldFunctions.length];
			for (int i = 0; i < toMultiply.length; i++)
				toMultiply[i] = new Pow(function1, oldFunctions[i]).simplify(settings);
			return new Product(toMultiply);
		} else {
			throw new IllegalCallerException("Distribute exponents called on a non-Product base.");
		}
	}

	/**
	 * Given a {@link Pow}, checks if the exponent is a positive integer, and if so, applies {@link #unwrapIntegerPower(SimplificationSettings)}
	 * @return {@code this} or a new unwrapped {@link Product}
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public GeneralFunction unwrapIntegerPowerSafe(SimplificationSettings settings) {
		try {
			return unwrapIntegerPower(settings);
		} catch (IllegalArgumentException ignored) {
			return this;
		}
	}

	/**
	 * Given a {@link Pow} with positive integer exponent, unwraps it into a {@link Product}
	 * @return a new unwrapped {@link Product}
	 * @throws RuntimeException if the exponent is not a positive integer
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public Product unwrapIntegerPower(SimplificationSettings settings) throws RuntimeException {
		int integerExponent = ParsingTools.toInteger(((Constant) function1).constant);
		GeneralFunction[] toMultiply = new GeneralFunction[integerExponent];
		Arrays.fill(toMultiply, function2);
		return new Product(toMultiply);
	}


	/**
	 * If the exponent is a logarithm with the same base as the {@link Pow}, it returns the argument of the logarithm
	 * @return the argument of the logarithm in the exponent, if the exponent is a logarithm with the same base as the {@link Pow}
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public GeneralFunction simplifyLogsOfSameBase(SimplificationSettings settings) {
		if (function1 instanceof LogInterface log && log.base().equalsSimplified(function2))
			return log.argument();
		else
			return this;
	}

	public OutputFunction toOutputFunction() {
		OutputFunction first = function2.toOutputFunction();
		OutputFunction second = function1.toOutputFunction();
		return new OutputPow(maybeParenthesize(first), maybeParenthesize(second));
	}

	@Override
	public PowInterface newWith(GeneralFunction exponent) {
		return new Pow(function2, exponent);
	}

	@Override
	public GeneralFunction exponent() {
		return function1;
	}

	@Override
	public GeneralFunction base() {
		return function2;
	}

	private static class OutputPow extends OutputBinary {

		public OutputPow(OutputFunction base, OutputFunction power) {
			super("pow", base, power);
		}

		@Override
		public String toString() {
			return first + "^" + second;
		}

		@Override
		public String toLatex() {
			return "{" + first.toLatex() + "}^{" + second.toLatex() + "}";
		}

	}
}
