package functions.unitary.trig;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

public abstract class GeneralTrigFunction extends UnitaryFunction {

	public GeneralTrigFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the Class corresponding to the inverse of this trig function
	 * @return the inverse class
	 */
	public abstract Class<? extends GeneralTrigFunction> getInverse();
}
