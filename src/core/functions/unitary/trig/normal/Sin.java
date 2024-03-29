package core.functions.unitary.trig.normal;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.piecewise.DomainRestrictor;
import core.functions.unitary.trig.inverse.Asin;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Sin extends TrigFunction {

	/**
	 * Constructs a new {@link Sin}
	 * @param operand The function which sin is operating on
	 */
	public Sin(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sin of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(new Cos(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Sin(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Cos(operand));
	}

	public Class<?> getInverse() {
		return Asin.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new DomainRestrictor(insideFunction, a -> a >= -1 && a <= 1);
			else
				return insideFunction;
		} else
			return this;
	}
}
