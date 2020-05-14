package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.Integrable;
import functions.unitary.trig.GeneralTrigFunction;

public abstract class TrigFunction extends GeneralTrigFunction {

	/**
	 * Constructs a new TrigFunction
	 * @param operand The operand of the TrigFunction
	 */
	public TrigFunction(GeneralFunction operand) {
		super(operand);
	}
}
