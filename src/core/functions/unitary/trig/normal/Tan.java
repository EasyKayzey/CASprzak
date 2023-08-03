package core.functions.unitary.trig.normal;

import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.specialcases.Ln;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Atan;
import core.tools.defaults.DefaultFunctions;

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
	public double evaluate(Map<String, Double> variableValues) {
		return Math.tan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(new Pow(DefaultFunctions.TWO, new Sec(operand)), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Tan(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sec(operand)));
	}

	public Class<?> getInverse() {
		return Atan.class;
	}
}
