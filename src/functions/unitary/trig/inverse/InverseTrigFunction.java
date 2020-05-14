package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import tools.DefaultFunctions;

public abstract class InverseTrigFunction extends GeneralTrigFunction {
	/**
	 * Constructs a new {@link InverseTrigFunction}
	 * @param operand The operand of the {@code InverseTrigFunction}
	 */
	public InverseTrigFunction(GeneralFunction operand) {
		super(operand);
	}

	public GeneralFunction getElementaryIntegral() {
		return new Sum(new Product(operand, me(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, UnitaryFunction.newInstanceOf(getInverse(), me(operand))));
	}
}
