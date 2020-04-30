package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Acsc extends UnitaryFunction {
	/**
	 * Constructs a new Acsc
	 * @param function The function which arccsc is operating on
	 */
	public Acsc(Function function) {
		super(function);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acsc(operand);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(new Abs(operand), new Pow(new Constant(0.5), new Sum(new Pow(new Constant(2), operand), new Constant(-1))))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccsc of {@link #operand} evaluated
	 */
	@SuppressWarnings("RedundantSuppression")
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 1) {
			//noinspection DuplicateExpressions
			return Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			//noinspection DuplicateExpressions
			return -Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}
}
