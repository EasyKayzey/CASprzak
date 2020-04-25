package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sech extends UnitaryFunction {
	/**
	 * Constructs a new Sech
	 * @param function The function which sech is operating on
	 */
	public Sech(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Sech(function), new Tanh(function));
	}

	/**
	 * Returns the hyperbolic secant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sech of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.cosh(function.evaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sech(operand);
	}
}
