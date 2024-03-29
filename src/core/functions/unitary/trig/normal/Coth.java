package core.functions.unitary.trig.normal;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.piecewise.DomainRestrictor;
import core.functions.unitary.specialcases.Ln;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Acoth;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Coth extends TrigFunction {

	/**
	 * Constructs a new {@link Coth}
	 * @param operand The function which coth is operating on
	 */
	public Coth(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.TWO, new Csch(operand)));
	}

	/**
	 * Returns the hyperbolic cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the coth of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.tanh(operand.evaluate(variableValues));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Coth(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sinh(operand)));
	}

	public Class<?> getInverse() {
		return Acoth.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new DomainRestrictor(insideFunction, a -> a <= -1 || a >= 1);
			else
				return insideFunction;
		} else
			return this;
	}
}
