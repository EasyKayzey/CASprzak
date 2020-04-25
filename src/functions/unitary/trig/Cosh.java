package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.unitary.UnitaryFunction;

public class Cosh extends UnitaryFunction {
	/**
	 * Constructs a new Cosh
	 * @param function The function which cosh is operating on
	 */
	public Cosh(Function function) {
		super(function);
	}

	/**
	 * Returns the hyperbolic cosine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cosh of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.cosh(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Sinh(function), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cosh(operand);
	}

}
