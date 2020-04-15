package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Tan extends UnitaryFunction {
	public Tan(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.tan(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Pow(new Constant(2), new Sec(function)), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Tan(operand);
	}

}
