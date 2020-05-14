package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Abs extends PiecewiseFunction {
	/**
	 * Constructs a new {@link Abs}
	 * @param operand The function which absolute value is operating on
	 */
	public Abs(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.abs(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Sign(operand));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Abs(operand);
	}
}
