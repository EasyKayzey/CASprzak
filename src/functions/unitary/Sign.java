package functions.unitary;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Sign extends UnitaryFunction {
	public Sign(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.signum(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Constant(2), new Dirac(function));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sign(operand);
	}
}
