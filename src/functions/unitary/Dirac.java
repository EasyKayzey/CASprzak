package functions.unitary;

import functions.Function;
import functions.special.Constant;

public class Dirac extends UnitaryFunction {
	public Dirac(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		if (function.evaluate(variableValues) == 0)
			return Double.NaN;
		else
			return 0;
	}

	@Override
	public Function getDerivative(int varID) {
		return new Constant(0);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Dirac(operand);
	}
}

