package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

/**
 * The abstract {@link PiecewiseFunction} class represents any piece-wise function.
 */
public abstract class PiecewiseFunction extends UnitaryFunction {
	public PiecewiseFunction(GeneralFunction operand) {
		super(operand);
	}
}
