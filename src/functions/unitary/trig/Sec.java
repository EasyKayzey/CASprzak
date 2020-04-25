package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.unitary.UnitaryFunction;

public class Sec extends UnitaryFunction {
	/**
	 * Constructs a new Sec
	 * @param function The function which sec is operating on
	 */
	public Sec(Function function) {
		super(function);
	}

	/**
	 * Returns the secant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sec of {@link #function} evaluated
	 */
	@Override
	public double evaluate(double... variableValues) {
		return 1 / Math.cos(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Tan(function), new Sec(function), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sec(operand);
	}

}
