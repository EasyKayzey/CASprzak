package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Csch extends UnitaryFunction {
	/**
	 * Constructs a new Csch
	 * @param function The function which csch is operating on
	 */
	public Csch(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Csch(function), new Coth(function));
	}

	/**
	 * Returns the hyperbolic cosecant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the csch of {@link #function} evaluated
	 */
	@Override
	public double oldEvaluate(Map<Character, Double> variableValues) {
		return 1 / Math.sinh(function.oldEvaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Csch(operand);
	}
}
