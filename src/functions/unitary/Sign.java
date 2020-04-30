package functions.unitary;

import functions.Function;
import functions.commutative.Product;
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
		return Math.signum(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Constant(2), new Dirac(operand));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sign(operand);
	}
}
