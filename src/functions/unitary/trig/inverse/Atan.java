package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Tan;
import tools.DefaultFunctions;

import java.util.Map;


public class Atan extends InverseTrigFunction {

	/**
	 * Constructs a new {@code Atan}
	 * @param operand The function which arctan is operating on
	 */
	public Atan(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arctan of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.atan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, new Pow(DefaultFunctions.TWO, operand))));
	}

	@Override
	public UnitaryFunction me(GeneralFunction operand) {
		return new Atan(operand);
	}

	public Class<?> getInverse() {
		return Tan.class;
	}
}
