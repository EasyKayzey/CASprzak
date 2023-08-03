package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.binary.integer.Modulo;
import core.functions.commutative.Sum;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.normal.Sec;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Asec extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Asec}
	 * @param operand The function which arcsec is operating on
	 */
	public Asec(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(new Abs(operand), DefaultFunctions.sqrt(new Sum(new Pow(DefaultFunctions.TWO, operand), DefaultFunctions.NEGATIVE_ONE)))));
	}

	/**
	 * Returns the inverse secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsec of {@link #operand} evaluated
	 */
	@SuppressWarnings({"DuplicateExpressions", "RedundantSuppression"})
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 1) {
			return Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			return Math.PI + Math.asin(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Asec(operand);
	}

	public Class<?> getInverse() {
		return Sec.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return DefaultFunctions.subtract(DefaultFunctions.PI, new Abs(DefaultFunctions.subtract(new Modulo(DefaultFunctions.TWO_PI, insideFunction), DefaultFunctions.PI)));
			else
				return insideFunction;
		} else
			return this;
	}
}
