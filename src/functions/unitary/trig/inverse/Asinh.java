package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Sinh;

import java.util.Map;


public class Asinh extends InverseTrigFunction {

	/**
	 * Constructs a new Asinh
	 * @param operand The function which arcsinh is operating on
	 */
	public Asinh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), new Sum(new Constant(1), new Pow(new Constant(2), operand))));
	}

	/**
	 * Returns the inverse hyperbolic sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsinh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated + 1));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Asinh(operand);
	}

	public Class<?> getInverse() {
		return Sinh.class;
	}
}
