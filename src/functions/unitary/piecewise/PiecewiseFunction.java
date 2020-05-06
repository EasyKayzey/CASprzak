package functions.unitary.piecewise;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

public abstract class PiecewiseFunction extends UnitaryFunction {
	/**
	 * Constructs a new UnitaryFunction
	 *
	 * @param operand The {@link GeneralFunction} which will be operated on
	 */
	public PiecewiseFunction(GeneralFunction operand) {
		super(operand);
	}
}
