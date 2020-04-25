package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Coth extends UnitaryFunction {
	/**
	 * Constructs a new Coth
	 * @param function The function which coth is operating on
	 */
	public Coth(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(2), new Csch(function)));
	}

	/**
	 * Returns the hyperbolic cotangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the coth of {@link #function} evaluated
	 */
	@Override
	public double oldEvaluate(Map<Character, Double> variableValues) {
		return 1 / Math.tanh(function.oldEvaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Coth(operand);
	}
}
