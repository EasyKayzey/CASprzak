package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Acos extends UnitaryFunction {
	public Acos(Function function) {
		super(function);
	}

	@Override
	public double evaluate(double... variableValues) {
		return Math.acos(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Add(new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Acos(operand);
	}

}
