package functions.unitary.integer.combo;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;
import tools.MiscTools;
import tools.VariableTools;

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

	public long operate(int input) {
		return MiscTools.factorial(input);
	}
}
