package functions.unitary.trig;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Abs;
import functions.unitary.Ln;
import functions.unitary.UnitaryFunction;

import java.util.Map;


public class Coth extends TrigFunction {

	/**
	 * Constructs a new Coth
	 * @param operand The function which coth is operating on
	 */
	public Coth(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Pow(new Constant(2), new Csch(operand)));
	}

	/**
	 * Returns the hyperbolic cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the coth of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.tanh(operand.evaluate(variableValues));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Coth(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sinh(operand)));
	}

	public Class<? extends TrigFunction> getInverse() {
		return Acoth.class;
	}
}
