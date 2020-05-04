package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.Integrable;
import functions.unitary.trig.GeneralTrigFunction;

public abstract class TrigFunction extends GeneralTrigFunction implements Integrable {

	public TrigFunction(GeneralFunction operand) {
		super(operand);
	}
}
