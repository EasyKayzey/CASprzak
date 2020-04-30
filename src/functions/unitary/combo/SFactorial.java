package functions.unitary.combo;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;

public class SFactorial extends Factorial {
	/**
	 * Constructs a new UnitaryFunction
	 *
	 * @param function The {@link Function} which will be operated on
	 */
	public SFactorial(Function function) {
		super(function);
	}

	@Override
	public Function classForm() {
		return new Multiply(new Pow(new Constant(.5), new Multiply(new Constant(2), new Constant("pi"), function)), new Pow(function, new Multiply(function, new Pow(new Constant(-1), new Constant("e")))));
	}

	@Override
	public UnitaryFunction me(Function function) {
			return new SFactorial(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return classForm().getDerivative(varID);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double argument = function.evaluate(variableValues);
		return Math.sqrt(2 * Math.PI * argument) * Math.pow(argument / Math.E, argument);
	}
}
