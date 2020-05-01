package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Sin extends TrigFunction {
	/**
	 * Constructs a new Sin
	 * @param function The function which sin is operating on
	 */
	public Sin(Function function) {
		super(function);
	}

	/**
	 * Returns the sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link Function} at the point
	 * @return the sin of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public Function getDerivative(char varID) {
		return new Product(new Cos(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(Function operand) {
		return new Sin(operand);
	}

	public Function integrate() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Cos(operand));
	}

}
