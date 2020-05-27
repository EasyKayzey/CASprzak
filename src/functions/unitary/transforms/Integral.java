package functions.unitary.transforms;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.ArrayTools;
import tools.DefaultFunctions;
import tools.IntegralTools;
import tools.exceptions.IntegrationFailedException;
import tools.integration.StageOne;
import tools.singlevariable.NumericalIntegration;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class used to store functions in the integration pipeline, allowing users to utilize the methods in {@link tools.integration}.
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
	public GeneralFunction substituteVariable(String varID, GeneralFunction toReplace) {
		if (varID.equals(respectTo))
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
		return new Integral(operand.substituteVariable(varID, toReplace), respectTo);
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
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf");
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
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		Map<String, Double> newMap = new HashMap<>(variableValues);
		double bound = newMap.remove(respectTo);
		return NumericalIntegration.simpsonsRule(operand.setVariables(newMap), 0, bound);
	}


	@Override
	public Integral simplifyInternal() {
		return new Integral(IntegralTools.minimalSimplify(operand), respectTo);
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
		return StageOne.derivativeDivides(operand, respectTo).simplify();
	}

	public GeneralFunction simplify() {
		if (respectTo == null) {
			return simplifyInternal().fixNull();
		} else {
			return super.simplify();
		}
	}

	private GeneralFunction fixNull() {
		if (operand instanceof Differential diff) {
			return new Integral(DefaultFunctions.ONE, diff.respectTo).simplify();
		} else if (operand instanceof Product product) {
			GeneralFunction[] functions = product.getFunctions();
			for (int i = 0; i < functions.length; i++) {
				if (functions[i] instanceof Differential diff) {
					return new Integral(new Product(ArrayTools.removeFunctionAt(functions, i)), diff.respectTo).simplify();
				}
			}
			return this;
		} else {
			return this;
		}
	}
}
