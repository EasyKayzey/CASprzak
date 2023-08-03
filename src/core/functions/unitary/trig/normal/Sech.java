package core.functions.unitary.trig.normal;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.piecewise.DomainRestrictor;
import core.functions.unitary.trig.inverse.Asech;
import core.functions.unitary.trig.inverse.Atan;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Sech extends TrigFunction {

	/**
	 * Constructs a new {@link Sech}
	 * @param operand The function which sech is operating on
	 */
	public Sech(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Sech(operand), new Tanh(operand));
	}

	/**
	 * Returns the hyperbolic secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sech of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.cosh(operand.evaluate(variableValues));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Sech(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Atan(new Abs(new Sinh(operand)));
	}

	public Class<?> getInverse() {
		return Asech.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new DomainRestrictor(insideFunction, a -> a >= 0 && a <= 1);
			else
				return insideFunction;
		} else
			return this;
	}
}
