package show.ezkz.casprzak.core.functions.unitary.transforms;

import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.output.OutputBinary;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputString;
import show.ezkz.casprzak.core.tools.ArrayTools;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.MiscTools;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.core.tools.exceptions.IntegrationFailedException;
import show.ezkz.casprzak.core.tools.integration.StageOne;
import show.ezkz.casprzak.core.tools.singlevariable.NumericalIntegration;

import java.util.Map;

/**
 * A wrapper class used to store functions in the integration pipeline, allowing users to utilize the methods in {@link show.ezkz.casprzak.core.tools.integration}.
 */
public class Integral extends Transformation {
	/**
	 * Constructs a new {@link Integral}
	 * @param integrand The integrand of the {@link Integral}
	 * @param respectTo The variable that the {@link Integral} is with respect to
	 */
	public Integral(GeneralFunction integrand, String respectTo) {
		super(integrand, respectTo);
	}

	/**
	 * Constructs a new {@link Integral} whose {@code respectTo} is {@code null}. This WILL THROW AN EXCEPTION if any method is attempted that uses {@code respectTo}.
	 * @param integrand the integrand of the {@link Integral}
	 */
	public Integral(GeneralFunction integrand) {
		this(integrand, null);
	}

	@Override
	public String toString() {
		return "∫[" + operand.toString() + "]d" + respectTo;
	}

	@Override
	public UnitaryFunction clone() {
		return new Integral(operand.clone(), respectTo);
	}

	@Override
	public GeneralFunction substituteVariables(Map<String, ? extends GeneralFunction> toSubstitute) {
		if (toSubstitute.containsKey(respectTo))
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to.");
		return new Integral(operand.substituteVariables(toSubstitute), respectTo);
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof Integral integral)
			return respectTo.equals(integral.respectTo) && operand.equalsSimplified(integral.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(GeneralFunction that) {
		if (that instanceof Integral integral)
			if (respectTo.equals(integral.respectTo))
				return operand.compareTo(integral.operand);
			else
				return respectTo.compareTo(integral.respectTo);
		else
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf.");
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		if (varID.equals(respectTo))
			return operand;
		else
			return new Integral(operand.getSimplifiedDerivative(varID), respectTo);
	}

	/**
	 * Integrates the operand numerically from 0 to the value specified in the {@code Map} corresponding to {@code respectTo}
	 * @param variableValues the values of the variables of the {@link GeneralFunction} at the point
	 * @return the operand integrated numerically
	 */
	@SuppressWarnings("unchecked")
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double bound = variableValues.get(respectTo);
		Map<String, GeneralFunction> newMap = Map.ofEntries(variableValues.entrySet().stream()
				.filter(e -> !e.getKey().equals(respectTo))
				.map(e -> Map.entry(e.getKey(), new Constant(e.getValue())))
				.toArray(Map.Entry[]::new)
		);
		return NumericalIntegration.simpsonsRule(operand.substituteVariables(newMap), 0, bound);
	}


	@Override
	public Integral simplifyInternal(SimplificationSettings settings) {
		return new Integral(MiscTools.minimalSimplify(operand), respectTo);
	}


	public UnitaryFunction getInstance(GeneralFunction function) {
		return new Integral(function, respectTo);
	}

	/**
	 * Returns the integral of the integrand
	 * @return the integral of the integrand
	 * @throws IntegrationFailedException if the integral could not be found
	 */
	public GeneralFunction execute() throws IntegrationFailedException {
		return StageOne.derivativeDivides(operand, respectTo).simplify(DefaultSimplificationSettings.user);
	}

	public GeneralFunction simplify(SimplificationSettings settings) {
		if (respectTo == null) {
			return simplifyInternal(settings).fixNull(settings);
		} else {
			return super.simplify(settings);
		}
	}

	private GeneralFunction fixNull(SimplificationSettings settings) {
		if (operand instanceof Differential diff) {
			return new Integral(DefaultFunctions.ONE, diff.respectTo).simplify(settings);
		} else if (operand instanceof Product product) {
			GeneralFunction[] functions = product.getFunctions();
			for (int i = 0; i < functions.length; i++) {
				if (functions[i] instanceof Differential diff) {
					return new Integral(new Product(ArrayTools.removeFunctionAt(functions, i)), diff.respectTo).simplify(settings);
				}
			}
			return this;
		} else {
			return this;
		}
	}

	public OutputFunction toOutputFunction() {
		return new OutputIntegral(operand.toOutputFunction(), new OutputString(respectTo));
	}

	private static class OutputIntegral extends OutputBinary {

		public OutputIntegral(OutputFunction operand, OutputFunction respectTo) {
			super("integral", operand, respectTo);
		}

		@Override
		public String toString() {
			return "∫ [" + first + "] d" + second;
		}

		@Override
		public String toLatex() {
			return " \\int \\left( " + first.toLatex() + "\\right) \\ d" + second;
		}

	}
}
