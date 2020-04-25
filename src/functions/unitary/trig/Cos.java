package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Cos extends UnitaryFunction {
	/**
	 * Constructs a new Cos
	 * @param function The function which cos is operating on
	 */
	public Cos(Function function) {
		super(function);
	}

	/**
	 * Returns the cosine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cos of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.cos(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Sin(function), new Constant(-1), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cos(operand);
	}

}
