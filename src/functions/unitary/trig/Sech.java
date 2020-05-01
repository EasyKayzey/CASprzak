package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sech extends TrigFunction {
	static {
		inverse = Asech.class;
	}

	/**
	 * Constructs a new Sech
	 * @param operand The function which sech is operating on
	 */
	public Sech(Function operand) {
		super(operand);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Sech(operand), new Tanh(operand));
	}

	/**
	 * Returns the hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.cosh(operand.evaluate(variableValues));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Sech(operand);
	}

	@Override
	public Function integrate() {
		return new Atan(new Abs(new Sinh(operand)));
	}

	public Class<? extends TrigFunction> getInverse() {
		return Asech.class;
	}
}
