package functions.unitary.trig;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Acosh extends InverseTrigFunction {

	/**
	 * Constructs a new Acosh
	 * @param operand The function which arccosh is operating on
	 */
	public Acosh(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_HALF, new Sum(DefaultFunctions.ONE, new Product(DefaultFunctions.NEGATIVE_ONE, new Pow(DefaultFunctions.TWO, operand)))));
	}

	/**
	 * Returns the inverse hyperbolic cosine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the arccosh of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double functionEvaluated = operand.evaluate(variableValues);
		return Math.log(functionEvaluated + Math.sqrt(functionEvaluated * functionEvaluated - 1));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Acosh(operand);
	}

	public Class<? extends TrigFunction> getInverse() {
		return Cosh.class;
	}
}
