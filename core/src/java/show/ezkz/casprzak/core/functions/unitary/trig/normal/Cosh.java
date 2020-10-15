package show.ezkz.casprzak.core.functions.unitary.trig.normal;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.piecewise.DomainRestrictor;
import show.ezkz.casprzak.core.functions.unitary.trig.inverse.Acosh;

import java.util.Map;


public class Cosh extends TrigFunction {

	/**
	 * Constructs a new {@link Cosh}
	 * @param operand The function which cosh is operating on
	 */
	public Cosh(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the hyperbolic cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the cosh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.cosh(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(new Sinh(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Cosh(operand);
	}

	public Class<?> getInverse() {
		return Acosh.class;
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sinh(operand);
	}

	@Override
	public GeneralFunction simplifyInverse(SimplificationSettings settings) {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (settings.enforceDomainAndRange)
				return new DomainRestrictor(insideFunction, a -> a >= 1);
			else
				return insideFunction;
		} else
			return this;
	}
}
