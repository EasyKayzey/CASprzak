package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

import java.util.Map;


public class Ln extends SpecialCaseBinaryFunction {
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
}
