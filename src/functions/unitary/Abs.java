package functions.unitary;

import functions.Function;
import functions.commutative.Multiply;

public class Abs extends UnitaryFunction {
	public Abs(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.abs(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Sign(function));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Abs(operand);
	}
}
