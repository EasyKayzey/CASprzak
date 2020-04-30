package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Asinh extends UnitaryFunction {
	/**
	 * Constructs a new Asinh
	 * @param function The function which arcsinh is operating on
	 */
	public Asinh(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), new Sum(new Constant(1), new Pow(new Constant(2), function))));
	}

	/**
	 * Returns the inverse hyperbolic sine of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arcsinh of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated + 1));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Asinh(operand);
	}
}
