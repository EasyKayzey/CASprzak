package core.functions.unitary.trig.normal;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.piecewise.DomainRestrictor;
import core.functions.unitary.specialcases.Ln;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Asec;

import java.util.Map;


public class Sec extends TrigFunction {

	/**
	 * Constructs a new {@link Sec}
	 * @param operand The function which sec is operating on
	 */
	public Sec(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the secant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sec of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.cos(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(new Tan(operand), new Sec(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Sec(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return new Ln(new Abs(new Sum(new Sec(operand), new Tan(operand))));
	}

	public Class<?> getInverse() {
		return Asec.class;
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
