package show.ezkz.casprzak.core.functions.unitary.specialcases;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.BinaryFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

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
}
