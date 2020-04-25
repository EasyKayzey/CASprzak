package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Acos extends UnitaryFunction {
	/**
	 * Constructs a new Acos
	 * @param function The function which arccos is operating on
	 */
	public Acos(Function function) {
		super(function);
	}

	/**
	 * Returns the inverse cosine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccos of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.acos(function.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Add(new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Acos(operand);
	}

}
