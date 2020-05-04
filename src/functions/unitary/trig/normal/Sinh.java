package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.inverse.Asinh;

import java.util.Map;


public class Sinh extends TrigFunction {

	/**
	 * Constructs a new Sinh
	 * @param operand The function which sinh is operating on
	 */
	public Sinh(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the hyperbolic sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sinh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Cosh(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Sinh(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Cosh(operand);
	}

	public Class<?> getInverse() {
		return Asinh.class;
	}
}
