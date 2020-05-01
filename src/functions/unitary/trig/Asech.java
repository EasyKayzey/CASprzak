package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Asech extends TrigFunction {
	/**
	 * Constructs a new Asech
	 * @param operand The function which arcsech is operating on
	 */
	public Asech(Function operand) {
		super(operand);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(operand, new Pow(new Constant(0.5), new Sum(new Constant(1), new Product(new Constant(-1), new Pow(new Constant(2), operand)))))));
	}

	/**
	 * Returns the inverse hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arcsech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log((1 + Math.sqrt(functionEvaluated * functionEvaluated - 1)) / functionEvaluated);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Asech(operand);
	}
}
