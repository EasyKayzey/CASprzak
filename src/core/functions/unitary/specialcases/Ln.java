package core.functions.unitary.specialcases;

import core.functions.GeneralFunction;
import core.functions.binary.BinaryFunction;
import core.functions.binary.Logb;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.unitary.UnitaryFunction;
import core.tools.defaults.DefaultFunctions;
import core.tools.helperclasses.LogInterface;

import java.util.Map;


public class Ln extends SpecialCaseBinaryFunction implements LogInterface {
	/**
	 * Constructs a new {@link Ln}
	 * @param operand The function which natural log is operating on
	 */
	public Ln(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return Math.log(operand.evaluate(variableValues));
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new Product(operand.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(operand));
	}

	public UnitaryFunction getInstance(GeneralFunction operand) {
		return new Ln(operand);
	}

	public BinaryFunction getClassForm() {
		return new Logb(operand, DefaultFunctions.E);
	}

	public Class<?> getInverse() {
		return Exp.class;
	}


	@Override
	public GeneralFunction simplifyInverse() {
		if (operand instanceof Pow pow)
			return new Product(pow.getFunction1(), new Ln(pow.getFunction2())).simplify();
		else
			return super.simplifyInverse();
	}

	@Override
	public LogInterface newWith(GeneralFunction argument) {
		return new Ln(argument);
	}

	@Override
	public GeneralFunction argument() {
		return operand;
	}

	@Override
	public GeneralFunction base() {
		return DefaultFunctions.E;
	}
}
