package functions.unitary.trig;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Sin extends TrigFunction {

	/**
	 * Constructs a new Sin
	 * @param operand The function which sin is operating on
	 */
	public Sin(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sin of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Cos(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Sin(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Cos(operand));
	}

	public Class<? extends TrigFunction> getInverse() {
		return Asin.class;
	}
}
