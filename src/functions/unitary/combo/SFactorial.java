package functions.unitary.combo;

import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import tools.ParsingTools;
import tools.DefaultFunctions;

import java.util.Map;

public class SFactorial extends Factorial {

	public SFactorial(GeneralFunction operand) {
		super(operand);
	}


	public GeneralFunction classForm() {
		return new Product(new Pow(DefaultFunctions.HALF, new Product(DefaultFunctions.TWO, DefaultFunctions.PI, operand)), new Pow(operand, new Product(operand, DefaultFunctions.reciprocal(DefaultFunctions.E))));
	}

	public UnitaryFunction getInstance(GeneralFunction function) {
			return new SFactorial(function);
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return classForm().getDerivative(varID);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		double argument = operand.evaluate(variableValues);
		if (Settings.enforceIntegerOperations)
			argument = ParsingTools.toInteger(argument);
		double ans = Math.sqrt(2 * Math.PI * argument) * Math.pow(argument / Math.E, argument);
		if (Settings.enforceIntegerOperations)
			return Math.round(ans);
		else
			return ans;
	}
}
