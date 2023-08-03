package core.functions.unitary.piecewise;

import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Sign extends PiecewiseFunction {
	/**
	 * Constructs a new {@link Sign}
	 * @param operand The function which signum is operating on
	 */
	public Sign(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.signum(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.TWO, new Dirac(operand));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Sign(operand);
	}
}
