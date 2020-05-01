package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Acos extends TrigFunction {
	/**
	 * Constructs a new Acos
	 * @param function The function which arccos is operating on
	 */
	public Acos(Function function) {
		super(function);
	}

	/**
	 * Returns the inverse cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the arccos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.acos(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, (new Sum(DefaultFunctions.ONE, new Product(DefaultFunctions.NEGATIVE_ONE, new Pow(DefaultFunctions.TWO, operand))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Acos(operand);
	}

}
