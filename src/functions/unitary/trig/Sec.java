package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sec extends UnitaryFunction {
	/**
	 * Constructs a new Sec
	 * @param function The function which sec is operating on
	 */
	public Sec(Function function) {
		super(function);
	}

	/**
	 * Returns the secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sec of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Tan(operand), new Sec(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sec(operand);
	}

}
