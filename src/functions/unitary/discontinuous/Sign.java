package functions.unitary.discontinuous;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sign extends UnitaryFunction {
	/**
	 * Constructs a new Sign
	 * @param operand The function which sign is operating on
	 */
	public Sign(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.signum(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Constant(2), new Dirac(operand));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Sign(operand);
	}
}
