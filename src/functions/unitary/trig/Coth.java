package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Coth extends UnitaryFunction {
	/**
	 * Constructs a new Coth
	 * @param function The function which coth is operating on
	 */
	public Coth(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(2), new Csch(function)));
	}

	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.tanh(function.evaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Coth(operand);
	}
}
