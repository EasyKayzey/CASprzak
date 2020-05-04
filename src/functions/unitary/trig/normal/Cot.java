package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.discontinuous.Abs;
import functions.unitary.specialcases.Ln;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.inverse.Acot;
import tools.DefaultFunctions;

import java.util.Map;


public class Cot extends TrigFunction {

	/**
	 * Constructs a new Cot
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
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.tan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Constant(-1), new Pow(new Constant(2), new Csc(operand)), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Cot(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Ln(new Abs(new Csc(operand))));
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Acot.class;
	}
}
