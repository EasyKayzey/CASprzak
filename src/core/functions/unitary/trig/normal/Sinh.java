package core.functions.unitary.trig.normal;

import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Asinh;

import java.util.Map;


public class Sinh extends TrigFunction {

	/**
	 * Constructs a new {@link Sinh}
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
	public double evaluate(Map<String, Double> variableValues) {
		return Math.sinh(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(new Cosh(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Sinh(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Cosh(operand);
	}

	public Class<?> getInverse() {
		return Asinh.class;
	}
}
