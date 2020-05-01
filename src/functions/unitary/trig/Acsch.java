package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Acsch extends InverseTrigFunction {
	/**
	 * Constructs a new Acsch
	 * @param operand The function which arccsch is operating on
	 */
	public Acsch(Function operand) {
		super(operand);
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_ONE, new Product(operand, new Pow(DefaultFunctions.HALF, new Sum(DefaultFunctions.ONE, new Product(DefaultFunctions.NEGATIVE_ONE, new Pow(DefaultFunctions.TWO, operand)))))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccsch of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log((1 + Math.sqrt(functionEvaluated * functionEvaluated + 1)) / functionEvaluated);
	}

	@Override
	public UnitaryFunction me(Function operand) {
		return new Acsch(operand);
	}
}
