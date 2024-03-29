package core.functions.binary.integer;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.BinaryFunction;
import core.tools.defaults.DefaultFunctions;
import core.tools.ParsingTools;
import core.tools.VariableTools;
import core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

/**
 * {@link IntegerBinaryFunction} provides resources for subclasses to implement mathematical functions with integer-restricted domain.
 */
public abstract class IntegerBinaryFunction extends BinaryFunction {
	/**
	 * Constructs a new {@link IntegerBinaryFunction}
	 * @param function1 the first {@link GeneralFunction} in the binary operation
	 * @param function2 the second {@link GeneralFunction} in the binary operation
	 */
	public IntegerBinaryFunction(GeneralFunction function1, GeneralFunction function2) {
		super(function1, function2);
	}


	@Override
	public GeneralFunction getDerivative(String varID) {
		if (VariableTools.doesNotContainsVariable(function1, varID) && VariableTools.doesNotContainsVariable(function2, varID))
			return DefaultFunctions.ZERO;
		else
			throw new DerivativeDoesNotExistException(this);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("IntegerBinaryFunctions cannot be used if Settings.enforceIntegerOperations is not enabled.");
		double a = function1.evaluate(variableValues);
		double b = function2.evaluate(variableValues);
		if (!ParsingTools.isAlmostInteger(a) || !ParsingTools.isAlmostInteger(b))
			throw new IllegalArgumentException("Tried to evaluate an IntegerBinaryFunction with non-integer operand set " + a + ", " + b + "");
		return operate(ParsingTools.toInteger(b), ParsingTools.toInteger(a));
	}

	/**
	 * Performs the operation of this function on the inputs
	 * @param first the first input
	 * @param second the second input
	 * @return the operation applied to the inputs
	 */
	protected abstract int operate(int first, int second);
}
