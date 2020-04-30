package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Acsch extends UnitaryFunction {
	/**
	 * Constructs a new Acsch
	 * @param function The function which arccsch is operating on
	 */
	public Acsch(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(function, new Pow(new Constant(0.5), new Sum(new Constant(1), new Product(new Constant(-1), new Pow(new Constant(2), function)))))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #function} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccsch of {@link #function} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = function.evaluate(variableValues);
		return Math.log((1 + Math.sqrt(functionEvaluated * functionEvaluated + 1)) / functionEvaluated);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acsch(operand);
	}
}
