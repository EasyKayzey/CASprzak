package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Sin;
import tools.DefaultFunctions;

import java.util.Map;


public class Asin extends InverseTrigFunction {

	/**
	 * Constructs a new {@code Asin}
	 * @param operand The function which arcsin is operating on
	 */
	public Asin(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sin of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.asin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, (new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand))))));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Asin(operand);
	}

	public Class<?> getInverse() {
		return Sin.class;
	}
}
