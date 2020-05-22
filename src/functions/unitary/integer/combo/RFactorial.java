package functions.unitary.integer.combo;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;
import tools.ParsingTools;
import tools.VariableTools;

import java.util.Map;

/**
 * The standard recursive definition of factorial.
 */
public class RFactorial extends Factorial {

	public RFactorial(GeneralFunction operand) {
		super(operand);
	}


	public GeneralFunction classForm() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("RFactorial has no class form.");
	}

	public UnitaryFunction getInstance(GeneralFunction function) {
		return new RFactorial(function);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		if (VariableTools.doesNotContainsVariable(operand, varID))
			return DefaultFunctions.ZERO;
		else
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
