package show.ezkz.casprzak.core.functions.unitary.trig.inverse;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Csch;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import java.util.Map;


public class Acsch extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Acsch}
	 * @param operand The function which arccsch is operating on
	 */
	public Acsch(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(DefaultFunctions.NEGATIVE_ONE, operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(operand, DefaultFunctions.sqrt(new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand)))))));
	}

	/**
	 * Returns the inverse hyperbolic cosecant of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccsch of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		if (functionEvaluated > 0)
			return Math.log((1 + Math.sqrt(functionEvaluated * functionEvaluated + 1)) / functionEvaluated);
		else
			return Math.log((1 - Math.sqrt(functionEvaluated * functionEvaluated + 1)) / functionEvaluated);
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Acsch(operand);
	}

	public Class<?> getInverse() {
		return Csch.class;
	}
}
