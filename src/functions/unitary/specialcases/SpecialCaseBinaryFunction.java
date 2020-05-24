package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.Invertible;
import functions.binary.BinaryFunction;
import functions.unitary.UnitaryFunction;

/**
 * The abstract {@link SpecialCaseBinaryFunction} class represents what would typically be {@link BinaryFunction}s, but are useful enough to have to have their own {@link UnitaryFunction}s.
 * Ex: {@code ln} and {@code exp}
 */
public abstract class SpecialCaseBinaryFunction extends UnitaryFunction implements Invertible {

	/**
	 * Constructs a new {@link SpecialCaseBinaryFunction}
	 * @param operand The function which the special case binary function is operating on
	 */
	public SpecialCaseBinaryFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the BinaryFunction that this function is a special case of. Ex: {@code ln} becomes {@code logb_e}
	 * @return a new BinaryFunction
	 */
	public abstract BinaryFunction getClassForm();
}
