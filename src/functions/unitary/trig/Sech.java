package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Sech extends UnitaryFunction {
	public Sech(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Sech(function), new Tanh(function));
	}

	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.cosh(function.evaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sech(operand);
	}
}
