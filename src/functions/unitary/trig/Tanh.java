package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Tanh extends UnitaryFunction {
	/**
	 * Constructs a new Tanh
	 * @param function The function which tanh is operating on
	 */
	public Tanh(Function function) {
		super(function);
	}

	/**
	 * Returns the hyperbolic tangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the tanh of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.tanh(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-2), new Cosh(function)));
	}

	public UnitaryFunction me(Function operand) {
		return new Tanh(operand);
	}

}
