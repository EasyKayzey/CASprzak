package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Cot extends TrigFunction {
	static {
		inverse = Acot.class;
	}

	/**
	 * Constructs a new Cot
	 * @param operand The function which cot is operating on
	 */
	public Cot(Function operand) {
		super(operand);
	}

	/**
	 * Returns the cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the cot of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.tan(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), new Pow(new Constant(2), new Csc(operand)), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Cot(operand);
	}

	@Override
	public Function integrate() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Ln(new Abs(new Csc(operand))));
	}
	public Class<? extends TrigFunction> getInverse() {
		return Acot.class;
	}
}
