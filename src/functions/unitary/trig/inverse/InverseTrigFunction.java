package functions.unitary.trig.inverse;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.GeneralTrigFunction;
import tools.DefaultFunctions;

public abstract class InverseTrigFunction extends GeneralTrigFunction {
	/**
	 * Constructs a new InverseTrigFunction
	 * @param operand The operand of the InverseTrigFunction
	 */
	public InverseTrigFunction(GeneralFunction operand) {
		super(operand);
	}

	/**
	 * Returns a {@link GeneralFunction} that would be the integral of the function with {@link UnitaryFunction#operand} as the variable
	 * @return a {@link GeneralFunction} that would be the integral of the function with {@link UnitaryFunction#operand} as the variable
	 */
	public GeneralFunction getElementaryIntegral() {
		return new Sum(new Product(operand, me(operand)), new Product(DefaultFunctions.NEGATIVE_ONE, UnitaryFunction.newInstanceOf(getInverse(), me(operand))));
	}
}
