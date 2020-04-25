package functions.unitary;

import functions.Function;
import functions.commutative.Multiply;

import java.util.Map;


public class Abs extends UnitaryFunction {
	/**
	 * Constructs a new Abs
	 * @param function The function which absolute value is operating on
	 */
	public Abs(Function function) {
		super(function);
	}

	@Override
	public double oldEvaluate(Map<Character, Double> variableValues) {
		return Math.abs(function.oldEvaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Sign(function));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Abs(operand);
	}
}
