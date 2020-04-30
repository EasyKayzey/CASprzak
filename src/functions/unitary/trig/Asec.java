package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Asec extends UnitaryFunction {
	/**
	 * Constructs a new Asec
	 * @param function The function which arcsec is operating on
	 */
	public Asec(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(new Abs(function), new Pow(new Constant(0.5), new Sum(new Pow(new Constant(2), function), new Constant(-1))))));
	}

	/**
	 * Returns the inverse secant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arcsec of {@link #function} evaluated
	 */
	@SuppressWarnings({"DuplicateExpressions", "RedundantSuppression"})
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		if (functionEvaluated > 1) {
			return Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			return Math.PI + Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Asec(operand);
	}
}
