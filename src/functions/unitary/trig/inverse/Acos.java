package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Cos;
import tools.DefaultFunctions;

import java.util.Map;


public class Acos extends InverseTrigFunction {

	/**
	 * Constructs a new Acos
	 * @param operand The function which arccos is operating on
	 */
	public Acos(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.acos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return DefaultFunctions.negative(new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, (new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand)))))));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Acos(operand);
	}

	public Class<?> getInverse() {
		return Cos.class;
	}
}
