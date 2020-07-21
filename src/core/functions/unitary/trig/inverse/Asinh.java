package show.ezkz.casprzak.core.functions.unitary.trig.inverse;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Sinh;
import show.ezkz.casprzak.core.tools.DefaultFunctions;

import java.util.Map;


public class Asinh extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Asinh}
	 * @param operand The function which arcsinh is operating on
	 */
	public Asinh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, new Sum(DefaultFunctions.ONE, new Pow(DefaultFunctions.TWO, operand))));
	}

	/**
	 * Returns the inverse hyperbolic sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arcsinh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated + 1));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Asinh(operand);
	}

	public Class<?> getInverse() {
		return Sinh.class;
	}
}
