package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Sum;
import core.functions.commutative.Product;
import core.functions.endpoint.Constant;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.trig.normal.Sech;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Asech extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Asech}
	 * @param operand The function which arcsech is operating on
	 */
	public Asech(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(operand, DefaultFunctions.sqrt(new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(new Constant(2), operand)))))));
	}

	/**
	 * Returns the inverse hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log((1 + Math.sqrt(1 - functionEvaluated * functionEvaluated)) / functionEvaluated);
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Asech(operand);
	}

	public Class<?> getInverse() {
		return Sech.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new Abs(insideFunction);
			else
				return insideFunction;
		} else
			return this;
	}
}
