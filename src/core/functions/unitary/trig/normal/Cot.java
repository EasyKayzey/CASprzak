package core.functions.unitary.trig.normal;

import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.specialcases.Ln;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Acot;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Cot extends TrigFunction {

	/**
	 * Constructs a new {@link Cot}
	 * @param operand The function which cot is operating on
	 */
	public Cot(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the cot of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.tan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Pow(DefaultFunctions.TWO, new Csc(operand)), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Cot(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Ln(new Abs(new Csc(operand))));
	}

	public Class<?> getInverse() {
		return Acot.class;
	}
}
