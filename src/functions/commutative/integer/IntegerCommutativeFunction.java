package functions.commutative.integer;

import config.Settings;
import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import tools.DefaultFunctions;
import tools.ParsingTools;
import tools.VariableTools;

import java.util.Arrays;
import java.util.Map;

public abstract class IntegerCommutativeFunction extends CommutativeFunction {
	/**
	 * Constructs a new {@link IntegerCommutativeFunction}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 */
	public IntegerCommutativeFunction(GeneralFunction... functions) {
		super(functions);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		if (VariableTools.doesNotContainsVariable(this, varID))
			return DefaultFunctions.ZERO;
		else
			throw new UnsupportedOperationException("IntegerCommutativeFunctions have no derivative.");
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("IntegerCommutativeFunctions cannot be used if Settings.enforceIntegerOperations is not enabled.");
		int[] toOperate = Arrays.stream(functions).mapToInt(f -> ParsingTools.toInteger(f.evaluate(variableValues))).toArray();
		return operateInt(toOperate);
	}

	public double operate(double a, double b) {
		return operateInt(ParsingTools.toInteger(a), ParsingTools.toInteger(b));
	}

	/**
	 * Performs the operation of this function on the inputs
	 * @param operands the list of inputs
	 * @return the operation applied to the inputs
	 */
	protected abstract long operateInt(int... operands);
}
