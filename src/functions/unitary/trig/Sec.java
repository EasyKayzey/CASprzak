package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.Abs;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Sec extends TrigFunction {

	/**
	 * Constructs a new Sec
	 * @param operand The function which sec is operating on
	 */
	public Sec(Function operand) {
		super(operand);
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


	public Function integrate() {
		return new Ln(new Abs(new Sum(new Sec(operand), new Tan(operand))));
	}

	public Class<? extends TrigFunction> getInverse() {
		return Asec.class;
	}
}
