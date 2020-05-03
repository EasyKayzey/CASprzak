package functions.unitary;

import functions.Function;
import functions.commutative.Product;

import java.util.Map;


public class Abs extends UnitaryFunction {
	/**
	 * Constructs a new Abs
	 * @param operand The function which absolute value is operating on
	 */
	public Abs(Function operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.abs(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Sign(operand));
	}


	public UnitaryFunction me(Function operand) {
		return new Abs(operand);
	}
}
