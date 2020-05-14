package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Exp extends SpecialCaseBinaryFunction {
	/**
	 * Constructs a new {@link Exp}
	 * @param operand The function which the exponential is operating on
	 */
	public Exp(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.exp(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new Product(this, operand.getDerivative(varID));
	}

	public UnitaryFunction me(GeneralFunction operand) {
		return new Exp(operand);
	}

	public BinaryFunction getClassForm() {
		return new Pow(operand, DefaultFunctions.E);
	}

	public Class<?> getInverse() {
		return Ln.class;
	}
}
