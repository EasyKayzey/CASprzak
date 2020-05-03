package functions.unitary.trig;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

public abstract class InverseTrigFunction extends TrigFunction {
	public InverseTrigFunction(GeneralFunction operand) {
		super(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sum(new Product(operand, me(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, UnitaryFunction.newInstanceOf(getInverse(), me(operand))));
	}
}
