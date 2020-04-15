package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Csch extends UnitaryFunction {
	public Csch(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Csch(function), new Coth(function));
	}

	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.sinh(function.evaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Csch(operand);
	}
}
