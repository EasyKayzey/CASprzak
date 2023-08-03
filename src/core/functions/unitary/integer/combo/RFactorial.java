package core.functions.unitary.integer.combo;

import core.functions.GeneralFunction;
import core.functions.unitary.UnitaryFunction;
import core.tools.defaults.DefaultFunctions;
import core.tools.MiscTools;
import core.tools.VariableTools;
import core.tools.exceptions.DerivativeDoesNotExistException;

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
	public GeneralFunction getDerivative(String varID) {
		if (VariableTools.doesNotContainsVariable(operand, varID))
			return DefaultFunctions.ZERO;
		else
			throw new DerivativeDoesNotExistException(this);
	}

	public long operate(int input) {
		return MiscTools.factorial(input);
	}
}
