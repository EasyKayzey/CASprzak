package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import functions.unitary.trig.normal.Sin;

import java.util.Map;


public class Asin extends InverseTrigFunction {

	/**
	 * Constructs a new Asin
	 * @param operand The function which arcsin is operating on
	 */
	public Asin(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the inverse sine of the stored {@link #operand} evaluated
	 * @param variableValues The values of the variables of the {@link GeneralFunction} at the point
	 * @return the sin of {@link #operand} evaluated
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.asin(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(operand.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), (new Sum(new Constant(1), new Product(new Constant(-1), new Pow(new Constant(2), operand))))));
	}


	public UnitaryFunction me(GeneralFunction operand) {
		return new Asin(operand);
	}

	public Class<? extends GeneralTrigFunction> getInverse() {
		return Sin.class;
	}
}
