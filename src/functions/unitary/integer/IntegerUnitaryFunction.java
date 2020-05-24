package functions.unitary.integer;

import config.Settings;
import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;
import tools.ParsingTools;
import tools.VariableTools;

import java.util.Map;

public abstract class IntegerUnitaryFunction extends UnitaryFunction {
	/**
	 * Constructs a new {@link IntegerUnitaryFunction}
	 * @param operand The {@link GeneralFunction} which will be operated on
	 */
	public IntegerUnitaryFunction(GeneralFunction operand) {
		super(operand);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		if (VariableTools.doesNotContainsVariable(this, varID))
			return DefaultFunctions.ZERO;
		else
			throw new UnsupportedOperationException("IntegerUnitaryFunctions have no derivative.");
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("IntegerUnitaryFunction cannot be used if Settings.enforceIntegerOperations is not enabled.");
		double input = operand.evaluate(variableValues);
		if (!ParsingTools.isAlmostInteger(input))
			throw new IllegalArgumentException("Tried to evaluate an IntegerUnitaryFunction with non-integer operand " + input);
		return operate(ParsingTools.toInteger(input));
	}

	/**
	 * Performs the operation of this function
	 * @param input the value to be operated on
	 * @return the result of the operation
	 */
	public abstract long operate(int input);
}
