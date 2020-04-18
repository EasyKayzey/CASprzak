package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.UnitaryFunction;

public class Asec extends UnitaryFunction {
	/**
	 * Constructs a new Asec
	 * @param function The function which arcsec is operating on
	 */
	public Asec(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(int varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Multiply(new Abs(function), new Pow(new Constant(0.5), new Add(new Pow(new Constant(2), function), new Constant(-1))))));
	}

	@SuppressWarnings({"DuplicateExpressions", "RedundantSuppression"})
	@Override
	public double evaluate(double... variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		if (functionEvaluated > 1) {
			return Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			return Math.PI + Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Asec(operand);
	}
}
