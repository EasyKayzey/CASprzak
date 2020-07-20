package show.ezkz.casprzak.core.functions.unitary.specialcases;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.BinaryFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.tools.DefaultFunctions;

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
