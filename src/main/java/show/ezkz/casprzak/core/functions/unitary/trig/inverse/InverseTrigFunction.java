package show.ezkz.casprzak.core.functions.unitary.trig.inverse;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.UnitaryFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.GeneralTrigFunction;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputUnitary;
import show.ezkz.casprzak.core.tools.DefaultFunctions;

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

		@Override
		public String toLatex() {
			return "\\" + functionName + " \\left( " + operand.toLatex() + " \\right)";
		}


	}
}
