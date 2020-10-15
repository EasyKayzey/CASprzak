package show.ezkz.casprzak.core.functions.unitary.specialcases;

import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.BinaryFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.helperclasses.LogInterface;

import java.util.Map;

import static show.ezkz.casprzak.core.tools.defaults.DefaultFunctions.E;
import static show.ezkz.casprzak.core.tools.defaults.DefaultFunctions.ONE;


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
		return new Logb(operand, E);
	}

	public Class<?> getInverse() {
		return Exp.class;
	}


	@Override
	public GeneralFunction simplify(SimplificationSettings settings) {
		GeneralFunction current = super.simplify(settings);
		if ((!settings.simplifyFunctionsOfConstants || !settings.simplifyFunctionsOfSpecialConstants) && current instanceof Ln ln && ln.operand.equals(E))
			return ONE;
		return current;
	}

	@Override
	public GeneralFunction simplifyInverse(SimplificationSettings settings) {
		if (operand instanceof Pow pow)
			return new Product(pow.getFunction1(), new Ln(pow.getFunction2())).simplify(settings);
		else
			return super.simplifyInverse(settings);
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
		return E;
	}
}
