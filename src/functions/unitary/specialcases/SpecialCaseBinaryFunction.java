package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.Invertible;
import functions.binary.BinaryFunction;
import functions.unitary.UnitaryFunction;

public abstract class SpecialCaseBinaryFunction extends UnitaryFunction implements Invertible {

	public SpecialCaseBinaryFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the BinaryFunction that this function is a special case of. Ex: {@code ln} becomes {@code logb_e}
	 * @return a new BinaryFunction
	 */
	public abstract BinaryFunction getClassForm();
}
