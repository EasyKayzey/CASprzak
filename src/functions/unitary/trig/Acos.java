package functions.unitary.trig;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Acos extends UnitaryFunction {
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
		return new Product(Constant.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(Constant.NEGATIVE_HALF, (new Sum(Constant.ONE, new Product(Constant.NEGATIVE_ONE, new Pow(Constant.TWO, operand))))));
	}

	public UnitaryFunction me(Function operand) {
		return new Acos(operand);
	}

}
