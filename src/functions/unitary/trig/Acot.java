package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Acot extends UnitaryFunction {
	/**
	 * Constructs a new Acot
	 * @param function The function which arccot is operating on
	 */
	public Acot(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(Constant.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(Constant.NEGATIVE_ONE, new Sum(Constant.ONE, new Pow(Constant.TWO, operand))));
	}

	/**
	 * Returns the inverse cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccot of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated < 0) {
			return -0.5 * Math.PI - Math.atan(functionEvaluated);
		} else {
			return 0.5 * Math.PI - Math.atan(functionEvaluated);
		}
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acot(operand);
	}
}
