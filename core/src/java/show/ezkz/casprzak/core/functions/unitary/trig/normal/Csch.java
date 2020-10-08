package show.ezkz.casprzak.core.functions.unitary.trig.normal;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.inverse.Acsch;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Csch extends TrigFunction {

	/**
	 * Constructs a new {@link Csch}
	 * @param operand The function which csch is operating on
	 */
	public Csch(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), new Csch(operand), new Coth(operand));
	}

	/**
	 * Returns the hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the csch of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return 1 / Math.sinh(operand.evaluate(variableValues));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Csch(operand);
	}


	public GeneralFunction getElementaryIntegral() {
		return null;
	}

	public Class<?> getInverse() {
		return Acsch.class;
	}
}
