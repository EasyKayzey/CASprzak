package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.normal.Sech;

import java.util.Map;


public class Asech extends InverseTrigFunction {

	/**
	 * Constructs a new Asech
	 * @param operand The function which arcsech is operating on
	 */
	public Asech(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(new Constant(-1), operand.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(operand, new Pow(new Constant(0.5), new Sum(new Constant(1), new Product(new Constant(-1), new Pow(new Constant(2), operand)))))));
	}

	/**
	 * Returns the inverse hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log((1 + Math.sqrt(functionEvaluated * functionEvaluated - 1)) / functionEvaluated);
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Asech(operand);
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Sech.class;
	}
}
