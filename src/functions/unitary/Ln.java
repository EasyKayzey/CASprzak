package functions.unitary;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;

import java.util.Map;


public class Ln extends UnitaryFunction {
	/**
	 * Constructs a new Ln
	 * @param operand The function which natural log is operating on
	 */
	public Ln(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.log(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(new Constant(-1), operand));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Ln(operand);
	}

}
