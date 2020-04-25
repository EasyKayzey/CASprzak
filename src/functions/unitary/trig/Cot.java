package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Cot extends UnitaryFunction {
	/**
	 * Constructs a new Cot
	 * @param function The function which cot is operating on
	 */
	public Cot(Function function) {
		super(function);
	}

	/**
	 * Returns the cotangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cot of {@link #function} evaluated
	 */
	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.tan(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), new Pow(new Constant(2), new Csc(function)), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cot(operand);
	}

}
