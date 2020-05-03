package functions.unitary.trig;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.Abs;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Acsc extends InverseTrigFunction {

	/**
	 * Constructs a new Acsc
	 * @param operand The function which arccsc is operating on
	 */
	public Acsc(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_ONE, new Product(new Abs(operand), new Pow(DefaultFunctions.HALF, new Sum(new Pow(DefaultFunctions.TWO, operand), DefaultFunctions.NEGATIVE_ONE)))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccsc of {@link #operand} evaluated
	 */
	@SuppressWarnings("RedundantSuppression")
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 1) {
			//noinspection DuplicateExpressions
			return Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			//noinspection DuplicateExpressions
			return -Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Acsc(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Csc.class;
	}
}
