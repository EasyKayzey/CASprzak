package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Acot extends UnitaryFunction {
	/**
	 * Constructs a new Acot
	 * @param function The function which arccot is operating on
	 */
	public Acot(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Pow(new Constant(2), function))));
	}

	/**
	 * Returns the inverse cotangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccot of {@link #function} evaluated
	 */
	@Override
	public double evaluate(double... variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		if (functionEvaluated < 0) {
			return -0.5 * Math.PI - Math.atan(functionEvaluated);
		} else {
			return 0.5 * Math.PI - Math.atan(functionEvaluated);
		}
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acot(operand);
	}
}
