package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Csc extends TrigFunction {

	/**
	 * Constructs a new Csc
	 * @param operand The function which csc is operating on
	 */
	public Csc(Function operand) {
		super(operand);
	}

	/**
	 * Returns the cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the csc of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), new Cot(operand), new Csc(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Csc(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Acsc.class;
	}
}