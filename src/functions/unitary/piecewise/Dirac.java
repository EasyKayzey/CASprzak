package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Dirac extends PiecewiseFunction {
	/**
	 * Constructs a new Dirac
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
		return DefaultFunctions.ZERO;
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Dirac(operand);
	}
}

