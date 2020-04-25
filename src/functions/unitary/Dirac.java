package functions.unitary;

import functions.Function;
import functions.special.Constant;

public class Dirac extends UnitaryFunction {
	/**
	 * Constructs a new Dirac
	 * @param function The function which the Dirac-Delta function is operating on
	 */
	public Dirac(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		if (function.evaluate(variableValues) == 0)
			return Double.POSITIVE_INFINITY;
		else
			return 0;
	}

	@Override
	public Function getDerivative(char varID) {
		return new Constant(0);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Dirac(operand);
	}
}

