package functions.unitary;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Cos extends UnitaryFunction {
	public Cos(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.cos(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Sin(function), new Constant(-1), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cos(operand);
	}

}
