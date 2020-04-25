package functions.unitary.trig;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Csc extends UnitaryFunction {
	/**
	 * Constructs a new Csc
	 * @param function The function which csc is operating on
	 */
	public Csc(Function function) {
		super(function);
	}

	/**
	 * Returns the cosecant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the csc of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.sin(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), new Cot(function), new Csc(function), function.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Csc(operand);
	}

}