package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Acosh extends TrigFunction {
	/**
	 * Constructs a new Acosh
	 * @param function The function which arccosh is operating on
	 */
	public Acosh(Function function) {
		super(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, new Sum(DefaultFunctions.ONE, new Product(DefaultFunctions.NEGATIVE_ONE, new Pow(DefaultFunctions.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccosh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated - 1));
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acosh(operand);
	}
}
