package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Dirac extends PiecewiseFunction {
	/**
	 * Constructs a new {@link Dirac}
	 * @param operand The function which the Dirac-Delta function is operating on
	 */
	public Dirac(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (operand.evaluate(variableValues) == 0)
			return Double.POSITIVE_INFINITY;
		else
			return 0;
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		throw new UnsupportedOperationException("The derivative of the dirac delta function is not supported.");
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Dirac(operand);
	}
}

