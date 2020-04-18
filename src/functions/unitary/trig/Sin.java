package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.unitary.UnitaryFunction;

public class Sin extends UnitaryFunction {
	/**
	 * Constructs a new Sin
	 * @param function The function which sin is operating on
	 */
	public Sin(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.sin(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Cos(function), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sin(operand);
	}

}
