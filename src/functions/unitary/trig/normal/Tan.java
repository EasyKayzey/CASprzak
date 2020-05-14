package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.unitary.piecewise.Abs;
import functions.unitary.specialcases.Ln;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.inverse.Atan;
import tools.DefaultFunctions;

import java.util.Map;


public class Tan extends TrigFunction {

	/**
	 * Constructs a new {@link Tan}
	 * @param operand The function which tan is operating on
	 */
	public Tan(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the tan of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.tan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Pow(DefaultFunctions.TWO, new Sec(operand)), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Tan(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sec(operand)));
	}

	public Class<?> getInverse() {
		return Atan.class;
	}
}
