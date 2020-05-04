package functions.unitary.specialcases;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

public abstract class SpecialCaseBinaryFunction extends UnitaryFunction {

	public SpecialCaseBinaryFunction(GeneralFunction operand) {
		super(operand);
	}

	public abstract GeneralFunction getClassForm();
}
