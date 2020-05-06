package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Cot;
import tools.DefaultFunctions;

import java.util.Map;


public class Acot extends InverseTrigFunction {

	/**
	 * Constructs a new Acot
	 * @param operand The function which arccot is operating on
	 */
	public Acot(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, new Pow(DefaultFunctions.TWO, operand))));
	}

	/**
	 * Returns the inverse cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccot of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated < 0) {
			return -0.5 * Math.PI - Math.atan(functionEvaluated);
		} else {
			return 0.5 * Math.PI - Math.atan(functionEvaluated);
		}
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Acot(operand);
	}

	public Class<?> getInverse() {
		return Cot.class;
	}
}
