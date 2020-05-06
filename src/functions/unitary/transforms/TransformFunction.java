package functions.unitary.transforms;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

public abstract class TransformFunction extends UnitaryFunction {
	/**
	 * The character of the variable that the Integral is with respect to
	 */
	public final char respectTo;

	/**
	 * Constructs a new CalcFunction
	 * @param operand The operand of the CalcFunction
	 * @param respectTo The variable that the CalcFunction operates with respect to
	 */
	public TransformFunction(GeneralFunction operand, char respectTo) {
		super(operand);
		this.respectTo = respectTo;
	}

	/**
	 * Returns the transformation of the {@link UnitaryFunction#operand}
	 * @return The the transformation of the {@link UnitaryFunction#operand}
	 */
	@SuppressWarnings("unused")
	public abstract GeneralFunction execute();
}
