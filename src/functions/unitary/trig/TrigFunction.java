package functions.unitary.trig;

import functions.Function;
import functions.unitary.UnitaryFunction;

public abstract class TrigFunction extends UnitaryFunction {

	public TrigFunction(Function operand) {
		super(operand);
	}

	/**
	 * The class representing the inverse of this function
	 */
	public static Class<? extends TrigFunction> inverse;
}
