package functions.unitary;

import functions.GeneralFunction;
import functions.commutative.Product;

import java.util.Map;


public class Exp extends UnitaryFunction {
	/**
	 * Constructs a new Ln
	 * @param operand The function which the exponential is operating on
	 */
	public Exp(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.exp(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(clone(), operand.getDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Exp(operand);
	}

}
