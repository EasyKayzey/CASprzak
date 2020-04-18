package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Atanh extends UnitaryFunction {
	/**
	 * Constructs a new Atanh
	 * @param function The function which arctanh is operating on
	 */
	public Atanh(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function)))));
	}

	@Override
	public double evaluate(double... variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		return 0.5 * Math.log((1 + functionEvaluated) / (1 + functionEvaluated));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Atanh(operand);
	}
}
