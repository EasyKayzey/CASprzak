package functions.binary.integer;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import tools.DefaultFunctions;
import tools.ParsingTools;
import tools.VariableTools;

import java.util.Map;

public abstract class IntegerBinaryFunction extends BinaryFunction {
	/**
	 * Constructs a new IntegerBinaryFunction
	 * @param function1 The first {@link GeneralFunction} in the binary operation
	 * @param function2 The second {@link GeneralFunction} in the binary operation
	 */
	public IntegerBinaryFunction(GeneralFunction function1, GeneralFunction function2) {
		super(function1, function2);
	}


	@Override
	public GeneralFunction getDerivative(char varID) {
		if (VariableTools.doesNotContainsVariable(function1, varID) && VariableTools.doesNotContainsVariable(function2, varID))
			return DefaultFunctions.ZERO;
		else
			throw new UnsupportedOperationException("IntegerBinaryFunctions have no derivative.");
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("IntegerQuotient cannot be used if Settings.enforceIntegerOperations is not enabled.");
		int argument1 = ParsingTools.toInteger(function1.evaluate(variableValues));
		int argument2 = ParsingTools.toInteger(function2.evaluate(variableValues));
		return operate(argument1, argument2);
	}

	/**
	 * Performs the operation of this function on the inputs
	 * @param first the first input
	 * @param second the second input
	 * @return the operation applied to the inputs
	 */
	protected abstract int operate(int first, int second);
}
