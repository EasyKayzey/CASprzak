package show.ezkz.casprzak.core.functions.unitary.trig.inverse;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.piecewise.Abs;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Cosh;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Acosh extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Acosh}
	 * @param operand The function which arccosh is operating on
	 */
	public Acosh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccosh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated - 1));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Acosh(operand);
	}

	public Class<?> getInverse() {
		return Cosh.class;
	}

	@Override
	public GeneralFunction simplifyInverse(SimplificationSettings settings) {
		if (operand.getClass().isAssignableFrom(getInverse())) {
			GeneralFunction insideFunction = ((UnitaryFunction) operand).operand;
			if (Settings.enforceDomainAndRange)
				return new Abs(insideFunction);
			else
				return insideFunction;
		} else
			return this;
	}
}
