package show.ezkz.casprzak.core.functions.unitary.integer.combo;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.MiscTools;
import show.ezkz.casprzak.core.tools.VariableTools;
import show.ezkz.casprzak.core.tools.exceptions.DerivativeDoesNotExistException;

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
