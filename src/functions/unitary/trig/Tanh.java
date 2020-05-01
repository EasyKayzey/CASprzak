package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Tanh extends TrigFunction {
	/**
	 * Constructs a new Tanh
	 * @param function The function which tanh is operating on
	 */
	public Tanh(Function function) {
		super(function);
	}

	/**
	 * Returns the hyperbolic tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the tanh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.tanh(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(new Constant(-2), new Cosh(operand)));
	}

	public UnitaryFunction me(Function operand) {
		return new Tanh(operand);
	}

	@Override
	public Function integrate() {
		return new Ln(new Cosh(operand));
	}
}
