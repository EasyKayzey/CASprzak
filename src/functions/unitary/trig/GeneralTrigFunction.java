package functions.unitary.trig;

import functions.GeneralFunction;
import functions.Integrable;
import functions.Invertible;
import functions.unitary.UnitaryFunction;

public abstract class GeneralTrigFunction extends UnitaryFunction implements Invertible, Integrable {

	/**
	 * Constructs a new {@link GeneralTrigFunction}
	 * @param operand The operand of the {@code GeneralTrigFunction}
	 */
	public GeneralTrigFunction(GeneralFunction operand) {
		super(operand);
	}

}
