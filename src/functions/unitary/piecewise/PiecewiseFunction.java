package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

/**
 * The abstract {@link PiecewiseFunction} class represents any piece-wise function.
 */
public abstract class PiecewiseFunction extends UnitaryFunction {
	/**
	 * Constructs a new {@link PiecewiseFunction}
	 * @param operand The function which the piecewise function is operating on
	 */
	public PiecewiseFunction(GeneralFunction operand) {
		super(operand);
	}
}
