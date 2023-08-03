package core.functions.unitary.trig.inverse;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.binary.integer.Modulo;
import core.functions.commutative.Sum;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.functions.unitary.trig.normal.Cot;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Acot extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Acot}
	 * @param operand The function which arccot is operating on
	 */
	public Acot(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, new Pow(DefaultFunctions.TWO, operand))));
	}

	/**
	 * Returns the inverse cotangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccot of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return 0.5 * Math.PI - Math.atan(functionEvaluated);
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Acot(operand);
	}

	public Class<?> getInverse() {
		return Cot.class;
	}

	@Override
	public GeneralFunction simplifyInverse() {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new Modulo(DefaultFunctions.PI, insideFunction);
			else
				return insideFunction;
		} else
			return this;
	}
}
