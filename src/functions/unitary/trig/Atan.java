package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Atan extends UnitaryFunction {
	/**
	 * Constructs a new Atan
	 * @param function The function which arctan is operating on
	 */
	public Atan(Function function) {
		super(function);
	}

	/**
	 * Returns the inverse tangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arctan of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.atan(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Pow(new Constant(2), function))));
	}

	public UnitaryFunction me(Function operand) {
		return new Atan(operand);
	}

}
