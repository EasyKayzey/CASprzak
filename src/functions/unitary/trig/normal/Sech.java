package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.discontinuous.Abs;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.inverse.Asech;
import functions.unitary.trig.inverse.Atan;

import java.util.Map;


public class Sech extends TrigFunction {

	/**
	 * Constructs a new Sech
	 * @param operand The function which sech is operating on
	 */
	public Sech(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Sech(operand), new Tanh(operand));
	}

	/**
	 * Returns the hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.cosh(operand.evaluate(variableValues));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Sech(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Atan(new Abs(new Sinh(operand)));
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Asech.class;
	}
}
