package core.functions.unitary.trig.normal;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.unitary.piecewise.Abs;
import core.functions.unitary.piecewise.DomainRestrictor;
import core.functions.unitary.specialcases.Ln;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.inverse.Acsc;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Csc extends TrigFunction {


	/**
	 * Constructs a new {@link Csc}
	 * @param operand The function which csc is operating on
	 */
	public Csc(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the csc of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.sin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Cot(operand), new Csc(operand), operand.getSimplifiedDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Csc(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Product(DefaultFunctions.NEGATIVE_ONE, new Ln(new Abs(new Sum(new Csc(operand), new Cot(operand)))));
	}

	public Class<?> getInverse() {
		return Acsc.class;
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
