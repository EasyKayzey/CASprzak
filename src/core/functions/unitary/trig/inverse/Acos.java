package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.binary.integer.Modulo;
import core.functions.commutative.Sum;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.trig.normal.Cos;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Acos extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Acos}
	 * @param operand The function which arccos is operating on
	 */
	public Acos(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.acos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, (new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand))))));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Acos(operand);
	}

	public Class<?> getInverse() {
		return Cos.class;
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
