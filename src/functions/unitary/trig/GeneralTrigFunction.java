package functions.unitary.trig;

import functions.GeneralFunction;
import functions.Invertible;
import functions.unitary.UnitaryFunction;

public abstract class GeneralTrigFunction extends UnitaryFunction implements Invertible {

	public GeneralTrigFunction(GeneralFunction operand) {
		super(operand);
	}

}
