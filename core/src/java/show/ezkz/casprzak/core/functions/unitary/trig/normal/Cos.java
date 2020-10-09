package show.ezkz.casprzak.core.functions.unitary.trig.normal;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.piecewise.DomainRestrictor;
import show.ezkz.casprzak.core.functions.unitary.trig.inverse.Acos;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Cos extends TrigFunction {

	/**
	 * Constructs a new {@link Cos}
	 * @param operand The function which cos is operating on
	 */
	public Cos(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the cos of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Sin(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Cos(operand);
	}

	public Class<?> getInverse() {
		return Acos.class;
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sin(operand);
	}

	@Override
	public GeneralFunction simplifyInverse(SimplificationSettings settings) {
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
