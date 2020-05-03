package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Coth extends TrigFunction {

	/**
	 * Constructs a new Coth
	 * @param operand The function which coth is operating on
	 */
	public Coth(Function operand) {
		super(operand);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Pow(new Constant(2), new Csch(operand)));
	}

	/**
	 * Returns the hyperbolic cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the coth of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.tanh(operand.evaluate(variableValues));
	}


	public UnitaryFunction me(Function operand) {
		return new Coth(operand);
	}

	public Function integrate() {
		return new Ln(new Abs(new Sinh(operand)));
	}

	public Class<? extends TrigFunction> getInverse() {
		return Acoth.class;
	}
}
