package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.binary.integer.Modulo;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.normal.Tan;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Atan extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Atan}
	 * @param operand The function which arctan is operating on
	 */
	public Atan(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arctan of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.atan(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, new Pow(DefaultFunctions.TWO, operand))));
	}

	@Override
	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Atan(operand);
	}

	public Class<?> getInverse() {
		return Tan.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return DefaultFunctions.subtract(new Modulo(DefaultFunctions.PI, new Sum(insideFunction, DefaultFunctions.HALF_PI)), DefaultFunctions.HALF_PI);
			else
				return insideFunction;
		} else
			return this;
	}
}
