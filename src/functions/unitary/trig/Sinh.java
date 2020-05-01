package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sinh extends TrigFunction {

	/**
	 * Constructs a new Sinh
	 * @param operand The function which sinh is operating on
	 */
	public Sinh(Function operand) {
		super(operand);
	}

	/**
	 * Returns the hyperbolic sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sinh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Cosh(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sinh(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Asinh.class;
	}
}
