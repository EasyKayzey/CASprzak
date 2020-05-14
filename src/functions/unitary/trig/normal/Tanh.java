package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.unitary.specialcases.Ln;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.inverse.Atanh;
import tools.DefaultFunctions;

import java.util.Map;


public class Tanh extends TrigFunction {

	/**
	 * Constructs a new {@link Tanh}
	 * @param operand The function which tanh is operating on
	 */
	public Tanh(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the hyperbolic tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the tanh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.tanh(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_TWO, new Cosh(operand)));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Tanh(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Cosh(operand));
	}

	public Class<?> getInverse() {
		return Atanh.class;
	}
}
