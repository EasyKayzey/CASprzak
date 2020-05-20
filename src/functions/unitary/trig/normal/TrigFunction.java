package functions.unitary.trig.normal;

import functions.GeneralFunction;
import functions.unitary.trig.GeneralTrigFunction;

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
}
