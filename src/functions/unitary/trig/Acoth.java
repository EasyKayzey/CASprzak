package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

public class Acoth extends UnitaryFunction {
	/**
	 * Constructs a new Acoth
	 * @param function The function which arccoth is operating on
	 */
	public Acoth(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Multiply(new Constant(-1), new Pow(new Constant(2), function)))));
	}

	/**
	 * Returns the inverse hyperbolic cotangent of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccoth of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		return 0.5 * Math.log((1 + functionEvaluated) / (1 + functionEvaluated));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acoth(operand);
	}
}
