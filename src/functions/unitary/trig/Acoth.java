package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


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
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(Constant.NEGATIVE_ONE, new Sum(Constant.ONE, new Product(Constant.NEGATIVE_ONE, new Pow(Constant.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccoth of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return 0.5 * Math.log((1 + functionEvaluated) / (1 + functionEvaluated));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acoth(operand);
	}
}
