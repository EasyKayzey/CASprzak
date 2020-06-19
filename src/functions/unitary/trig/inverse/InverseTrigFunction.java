package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import output.OutputFunction;
import output.OutputUnitary;
import tools.DefaultFunctions;

/**
 * The abstract {@link InverseTrigFunction} class represents any inverse trigonometric function.
 */
public abstract class InverseTrigFunction extends GeneralTrigFunction {
	/**
	 * Constructs a new {@link InverseTrigFunction}
	 * @param operand The operand of the {@code InverseTrigFunction}
	 */
	public InverseTrigFunction(GeneralFunction operand) {
		super(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sum(new Product(operand, getInstance(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, UnitaryFunction.newInstanceOf(getInverse(), getInstance(operand))));
	}

	public OutputFunction toOutputFunction() {
		return new OutputInverseTrigFunction(operand.toOutputFunction(), this.getClass().getSimpleName().toLowerCase().substring(1));
	}

	private static class OutputInverseTrigFunction extends OutputUnitary {

		public OutputInverseTrigFunction(OutputFunction operand, String functionString) {
			super("arc" + functionString, operand);
		}

	}
}
