package show.ezkz.casprzak.core.functions.unitary.trig.normal;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.trig.GeneralTrigFunction;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputUnitary;

/**
 * The abstract {@link TrigFunction} class represents any non-inverse trigonometric function (circular or hyperbolic).
 */
public abstract class TrigFunction extends GeneralTrigFunction {

	/**
	 * Constructs a new {@link TrigFunction}
	 * @param operand The operand of the {@code TrigFunction}
	 */
	public TrigFunction(GeneralFunction operand) {
		super(operand);
	}

	public OutputFunction toOutputFunction() {
		return new OutputTrigFunction(operand.toOutputFunction(), this.getClass().getSimpleName().toLowerCase());
	}

	private static class OutputTrigFunction extends OutputUnitary {

		public OutputTrigFunction(OutputFunction operand, String functionString) {
			super(functionString, operand);
		}

		@Override
		public String toLatex() {
			return "\\" + functionName + " \\left( " + operand.toLatex() + " \\right)";
		}


	}
}
