package functions.unitary.combo;

import config.Settings;
import functions.Function;
import functions.unitary.UnitaryFunction;
import tools.MiscTools;

import java.util.Map;

public class RFactorial extends Factorial {

	public RFactorial(Function operand) {
		super(operand);
	}

	@Override
	public UnitaryFunction me(Function function) {
		return new RFactorial(function);
	}

	@Override
	public Function getDerivative(char varID) {
		throw new UnsupportedOperationException("RFactorial has no derivative.");
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("RFactorial cannot be used if Settings.enforceIntegerOperations is not enabled.");
		int argument = MiscTools.toInteger(operand.evaluate(variableValues));
		int prod = 1;
		for (int i = 1; i <= argument; i++)
			prod *= i;
		return prod;
	}
}
