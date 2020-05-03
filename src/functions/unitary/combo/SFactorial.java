package functions.unitary.combo;

import config.Settings;
import functions.Function;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;
import tools.MiscTools;

import java.util.Map;

public class SFactorial extends Factorial {
	/**
	 * Constructs a new UnitaryFunction
	 *
	 * @param operand The {@link Function} which will be operated on
	 */
	public SFactorial(Function operand) {
		super(operand);
	}

	private Function classForm() {
		return new Product(new Pow(new Constant(.5), new Product(new Constant(2), new Constant("pi"), operand)), new Pow(operand, new Product(operand, new Pow(new Constant(-1), new Constant("e")))));
	}


	public UnitaryFunction me(Function function) {
			return new SFactorial(function);
	}

	@Override
	public Function getDerivative(char varID) {
		return classForm().getDerivative(varID);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double argument = operand.evaluate(variableValues);
		if (Settings.enforceIntegerOperations)
			argument = MiscTools.toInteger(argument);
		double ans = Math.sqrt(2 * Math.PI * argument) * Math.pow(argument / Math.E, argument);
		if (Settings.enforceIntegerOperations)
			return (int) (ans + .5);
		else
			return ans;
	}
}
