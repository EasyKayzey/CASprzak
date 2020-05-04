package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.discontinuous.Abs;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.normal.Sec;
import tools.DefaultFunctions;

import java.util.Map;


public class Asec extends InverseTrigFunction {

	/**
	 * Constructs a new Asec
	 * @param operand The function which arcsec is operating on
	 */
	public Asec(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_ONE, new Product(new Abs(operand), new Pow(DefaultFunctions.HALF, new Sum(new Pow(DefaultFunctions.TWO, operand), DefaultFunctions.NEGATIVE_ONE)))));
	}

	/**
	 * Returns the inverse secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsec of {@link #operand} evaluated
	 */
	@SuppressWarnings({"DuplicateExpressions", "RedundantSuppression"})
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 1) {
			return Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			return Math.PI + Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Asec(operand);
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Sec.class;
	}
}
