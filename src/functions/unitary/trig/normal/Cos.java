package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.inverse.Acos;
import tools.DefaultFunctions;

import java.util.Map;


public class Cos extends TrigFunction {

	/**
	 * Constructs a new Cos
	 * @param operand The function which cos is operating on
	 */
	public Cos(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the cos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return DefaultFunctions.negative(new Product(new Sin(operand), operand.getSimplifiedDerivative(varID)));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Cos(operand);
	}

	public Class<?> getInverse() {
		return Acos.class;
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sin(operand);
	}
}
