package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Cot extends UnitaryFunction {
	public Cot(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.tan(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), new Pow(new Constant(2), new Csc(function)), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cot(operand);
	}

}
