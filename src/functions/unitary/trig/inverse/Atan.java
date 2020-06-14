package functions.unitary.trig.inverse;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.binary.integer.Modulo;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Tan;
import tools.DefaultFunctions;

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
