package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.piecewise.Abs;
import functions.unitary.specialcases.Ln;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.inverse.Asec;

import java.util.Map;


public class Sec extends TrigFunction {

	/**
	 * Constructs a new Sec
	 * @param operand The function which sec is operating on
	 */
	public Sec(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sec of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 1 / Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Tan(operand), new Sec(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Sec(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sum(new Sec(operand), new Tan(operand))));
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Asec.class;
	}
}
