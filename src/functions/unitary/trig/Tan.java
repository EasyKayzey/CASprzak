package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Tan extends UnitaryFunction {
	/**
	 * Constructs a new Tan
	 * @param function The function which tan is operating on
	 */
	public Tan(Function function) {
		super(function);
	}

	/**
	 * Returns the tangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the tan of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.tan(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Pow(new Constant(2), new Sec(function)), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Tan(operand);
	}

}
