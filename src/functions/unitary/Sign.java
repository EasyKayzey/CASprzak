package functions.unitary;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;

import java.util.Map;


public class Sign extends UnitaryFunction {
	/**
	 * Constructs a new Sign
	 * @param function The function which sign is operating on
	 */
	public Sign(Function function) {
		super(function);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.signum(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Constant(2), new Dirac(function));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sign(operand);
	}
}
