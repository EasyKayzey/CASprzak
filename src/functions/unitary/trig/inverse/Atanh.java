package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Tanh;
import tools.DefaultFunctions;

import java.util.Map;


public class Atanh extends InverseTrigFunction {

	/**
	 * Constructs a new Atanh
	 * @param operand The function which arctanh is operating on
	 */
	public Atanh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arctanh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return 0.5 * Math.log((1 + functionEvaluated) / (1 + functionEvaluated));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Atanh(operand);
	}

	public Class<?> getInverse() {
		return Tanh.class;
	}
}
