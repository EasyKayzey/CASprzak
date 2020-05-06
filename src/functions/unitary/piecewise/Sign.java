package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Sign extends PiecewiseFunction {
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
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.TWO, new Dirac(operand));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Sign(operand);
	}
}
