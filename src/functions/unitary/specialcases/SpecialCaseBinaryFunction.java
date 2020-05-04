package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.binary.BinaryFunction;
import functions.unitary.UnitaryFunction;

public abstract class SpecialCaseBinaryFunction extends UnitaryFunction {

	public SpecialCaseBinaryFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns the BinaryFunction this function is a special case of
	 * @return a BinaryFunction
	 */
	public abstract BinaryFunction getClassForm();
}
