package show.ezkz.casprzak.core.functions.binary;

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
import show.ezkz.casprzak.core.tools.helperclasses.LogInterface;

import java.util.Map;

import static show.ezkz.casprzak.core.output.ToStringManager.maybeParenthesize;

public class Logb extends BinaryFunction implements LogInterface {
	/**
	 * Constructs a new Logb
	 * @param argument The argument of the logarithm
	 * @param base The base of the logarithm
	 */
	public Logb(GeneralFunction argument, GeneralFunction base) {
		super(argument, base);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		if (function2 instanceof Constant base)
			return new Product(function1.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(new Ln(new Constant(base.constant)), function1)));
		else
			return new Product(new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2), DefaultFunctions.reciprocal(function1)), new Product(DefaultFunctions.NEGATIVE_ONE, function2.getSimplifiedDerivative(varID), new Ln(function1), DefaultFunctions.reciprocal(function2))), new Pow(DefaultFunctions.NEGATIVE_TWO, new Ln(function2)));
	}

	public BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2) {
		return new Logb(function1, function2);
	}

	public GeneralFunction clone() {
		return new Logb(function1.clone(), function2.clone());
	}

	public GeneralFunction toSpecialCase() {
		return new Product(new Ln(function1), DefaultFunctions.reciprocal(new Ln(function2)));
	}

	@Override
	public String toString() {
		return "(log_{" + function2.toString() + "}(" + function1.toString() + "))";
	}

	public GeneralFunction simplify(SimplificationSettings settings) {
		Logb currentLogb = new Logb(function1.simplify(settings), function2.simplify(settings));
		GeneralFunction current = currentLogb.simplifyPowers(settings);

		if (settings.simplifyFunctionsOfConstants && current instanceof Logb logb)
			current = logb.simplifyFOC(settings);

		if (current instanceof Logb logb)
			current = logb.simplifyIdentity(settings);

		return current;
	}

	/**
	 * Returns {@link DefaultFunctions#ONE} if the base of the logarithm equals the argument
	 * @return {@link DefaultFunctions#ONE} if the base of the logarithm equals the argument
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public GeneralFunction simplifyIdentity(SimplificationSettings settings) {
		if (function2.equalsSimplified(function1))
			return DefaultFunctions.ONE;
		else
			return this;
	}

	/**
	 * Returns a {@link GeneralFunction} where, if the argument is a {@link Pow} or {@link Exp}, the exponent of the argument has been moved in front of the logarithm in a {@link Product}
	 * @return a {@link GeneralFunction} where, if the argument is a {@link Pow} or {@link Exp}, the exponent of the argument has been moved in front of the logarithm in a {@link Product}
	 * @param settings the {@link SimplificationSettings} object describing the parameters of simplification
	 */
	public GeneralFunction simplifyPowers(SimplificationSettings settings) {
		if (function1 instanceof Pow power)
			return new Product(power.function1, new Logb(power.function2, function2)).simplify(settings);
		else if (function1 instanceof Exp exp)
			return new Product(exp.operand, new Logb(function2, DefaultFunctions.E)).simplify(settings);
		else
			return this;
	}

	public OutputFunction toOutputFunction() {
		return new OutputLogb(maybeParenthesize(function2.toOutputFunction()), function1.toOutputFunction());
	}

	@Override
	public LogInterface newWith(GeneralFunction argument) {
		return new Logb(argument, function2);
	}

	@Override
	public GeneralFunction argument() {
		return function1;
	}

	@Override
	public GeneralFunction base() {
		return function2;
	}

	private static class OutputLogb extends OutputBinary {

		public OutputLogb(OutputFunction base, OutputFunction power) {
			super("logb", base, power);
		}

		@Override
		public String toString() {
			return "log_{" + first + "}(" + second + ")";
		}

		@Override
		public String toLatex() {
			return "\\log_{ " + first.toLatex() + " }( " + second.toLatex() + " )";
		}

	}
}
