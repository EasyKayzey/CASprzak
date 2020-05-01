package functions.unitary.trig;

import functions.Function;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import tools.DefaultFunctions;

public abstract class InverseTrigFunction extends TrigFunction {
	public InverseTrigFunction(Function operand) {
		super(operand);
	}

	public Function integrate() {
		return new Sum(new Product(operand, me(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, UnitaryFunction.newInstanceOf(getInverse(), me(operand))));
	}
}
