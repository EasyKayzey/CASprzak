package functions.unitary.trig;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Atan extends InverseTrigFunction {

	/**
	 * Constructs a new Atan
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
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Sum(new Constant(1), new Pow(new Constant(2), operand))));
	}

	@Override
	public UnitaryFunction me(GeneralFunction operand) {
		return new Atan(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Tan.class;
	}
}
