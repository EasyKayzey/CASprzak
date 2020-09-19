package show.ezkz.casprzak.core.functions.unitary.trig.inverse;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Tanh;
import show.ezkz.casprzak.core.tools.DefaultFunctions;

import java.util.Map;


public class Atanh extends InverseTrigFunction {

	/**
	 * Constructs a new {@link Atanh}
	 * @param operand The function which arctanh is operating on
	 */
	public Atanh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Sum(DefaultFunctions.ONE, DefaultFunctions.negative(new Pow(DefaultFunctions.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic tangent of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arctanh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return 0.5 * Math.log((1 + functionEvaluated) / (1 - functionEvaluated));
	}


	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Atanh(operand);
	}

	public Class<?> getInverse() {
		return Tanh.class;
	}
}
