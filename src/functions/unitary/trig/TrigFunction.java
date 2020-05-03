package functions.unitary.trig;

import functions.GeneralFunction;
import functions.Integrable;
import functions.unitary.UnitaryFunction;

public abstract class TrigFunction extends UnitaryFunction implements Integrable {

	public TrigFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the Class corresponding to the inverse of this trig function
	 * @return the inverse class
	 */
	public abstract Class<? extends TrigFunction> getInverse();
}
