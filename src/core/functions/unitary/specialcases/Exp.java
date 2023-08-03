package core.functions.unitary.specialcases;

import core.functions.GeneralFunction;
import core.functions.binary.BinaryFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.tools.defaults.DefaultFunctions;
import core.tools.helperclasses.PowInterface;

import java.util.Map;


public class Exp extends SpecialCaseBinaryFunction implements PowInterface {
	/**
	 * Constructs a new {@link Exp}
	 * @param operand The function which the exponential is operating on
	 */
	public Exp(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.exp(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(this, operand.getDerivative(varID));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Exp(operand);
	}

	public BinaryFunction getClassForm() {
		return new Pow(operand, DefaultFunctions.E);
	}

	public Class<?> getInverse() {
		return Ln.class;
	}

	@Override
	public PowInterface newWith(GeneralFunction exponent) {
		return new Exp(exponent);
	}

	@Override
	public GeneralFunction exponent() {
		return operand;
	}

	@Override
	public GeneralFunction base() {
		return DefaultFunctions.E;
	}
}
