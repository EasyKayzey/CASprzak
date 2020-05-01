package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Cos extends TrigFunction {
	static {
		inverse = Acos.class;
	}

	/**
	 * Constructs a new Cos
	 * @param operand The function which cos is operating on
	 */
	public Cos(Function operand) {
		super(operand);
	}

	/**
	 * Returns the cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Sin(operand), new Constant(-1), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cos(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Acos.class;
	}
	@Override
	public Function integrate() {
		return new Sin(operand);
	}
}
