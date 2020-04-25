package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Asin extends UnitaryFunction {
	/**
	 * Constructs a new Asin
	 * @param function The function which arcsin is operating on
	 */
	public Asin(Function function) {
		super(function);
	}

	/**
	 * Returns the inverse sine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sin of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.asin(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Add(new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Asin(operand);
	}

}
