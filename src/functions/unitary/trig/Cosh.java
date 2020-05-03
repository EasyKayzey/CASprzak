package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Cosh extends TrigFunction {

	/**
	 * Constructs a new Cosh
	 * @param operand The function which cosh is operating on
	 */
	public Cosh(Function operand) {
		super(operand);
	}

	/**
	 * Returns the hyperbolic cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cosh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.cosh(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Sinh(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cosh(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Acosh.class;
	}

	public Function getElementaryIntegral() {
		return new Sinh(operand);
	}
}
