package show.ezkz.casprzak.core.functions.commutative.integer;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.CommutativeFunction;
import show.ezkz.casprzak.core.tools.DefaultFunctions;
import show.ezkz.casprzak.core.tools.ParsingTools;
import show.ezkz.casprzak.core.tools.VariableTools;
import show.ezkz.casprzak.core.tools.exceptions.DerivativeDoesNotExistException;

import java.util.Arrays;
import java.util.Map;

/**
 * {@link IntegerCommutativeFunction} provides resources for subclasses to implement mathematical functions with integer-restricted domain.
 */
public abstract class IntegerCommutativeFunction extends CommutativeFunction {
	/**
	 * Constructs a new {@link IntegerCommutativeFunction}
	 * @param functions the {@link GeneralFunction}s that will be acted on
	 */
	public IntegerCommutativeFunction(GeneralFunction... functions) {
		super(functions);
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		if (VariableTools.doesNotContainsVariable(this, varID))
			return DefaultFunctions.ZERO;
		else
			throw new DerivativeDoesNotExistException(this);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		if (!Settings.enforceIntegerOperations)
			throw new IllegalStateException("IntegerCommutativeFunctions cannot be used if Settings.enforceIntegerOperations is not enabled.");
		int[] toOperate = Arrays.stream(functions)
				.mapToInt(f -> ParsingTools.toInteger(f.evaluate(variableValues)))
				.toArray();
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
