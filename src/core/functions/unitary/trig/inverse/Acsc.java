package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.binary.integer.Modulo;
import core.functions.commutative.Sum;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.normal.Csc;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Acsc extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Acsc}
	 * @param operand The function which arccsc is operating on
	 */
	public Acsc(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(new Abs(operand), new Pow(DefaultFunctions.HALF, new Sum(new Pow(DefaultFunctions.TWO, operand), DefaultFunctions.NEGATIVE_ONE)))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccsc of {@link #operand} evaluated
	 */
	@SuppressWarnings("RedundantSuppression")
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 1) {
			return Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else if (functionEvaluated < -1) {
			return -Math.acos(-Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
		} else {
			return Double.NaN;
		}
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Acsc(operand);
	}

	public Class<?> getInverse() {
		return Csc.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return DefaultFunctions.subtract(new Abs(DefaultFunctions.subtract(new Modulo(DefaultFunctions.TWO_PI, DefaultFunctions.subtract(insideFunction, DefaultFunctions.HALF_PI)), DefaultFunctions.PI)), DefaultFunctions.HALF_PI);
			else
				return insideFunction;
		} else
			return this;
	}
}
