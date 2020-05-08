package functions.unitary.combo;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.ParsingTools;

import java.util.Map;

public class RFactorial extends Factorial {

	public RFactorial(GeneralFunction operand) {
		super(operand);
	}


	public GeneralFunction classForm() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("RFactorial has no class form.");
	}

	public UnitaryFunction me(GeneralFunction function) {
		return new RFactorial(function);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		throw new UnsupportedOperationException("RFactorial has no derivative.");
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("RFactorial cannot be used if Settings.enforceIntegerOperations is not enabled.");
		int argument = ParsingTools.toInteger(operand.evaluate(variableValues));
		long prod = 1;
		for (int i = 2; i <= argument; i++)
			prod *= i;
		return prod;
	}
}
