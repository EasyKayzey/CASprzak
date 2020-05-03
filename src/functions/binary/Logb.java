package functions.binary;

import functions.GeneralFunction;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Ln;
import tools.DefaultFunctions;

import java.util.Map;

public class Logb extends BinaryFunction {
	/**
	 * Constructs a new Logb
	 * @param argument The argument of the logarithm
	 * @param base The base of the logarithm
	 */
	public Logb(GeneralFunction argument, GeneralFunction base) {
		super(argument, base);
	}


	@Override
	public String toString() {
		return "(log_{" + function2.toString() + "}(" + function1.toString() + "))";
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		if (function2 instanceof Constant base)
			return new Product(function1.getSimplifiedDerivative(varID), new Pow(DefaultFunctions.NEGATIVE_ONE, new Product(new Ln(new Constant(base.constant)), function1)));
		else
			return new Product(new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2), new Pow(DefaultFunctions.NEGATIVE_ONE, function1)), new Product(DefaultFunctions.NEGATIVE_ONE, function2.getSimplifiedDerivative(varID), new Ln(function1), new Pow(DefaultFunctions.NEGATIVE_ONE, function2))), new Pow(new Constant(-2), new Ln(function2)));
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
	}

	public GeneralFunction clone() {
		return new Logb(function1.clone(), function2.clone());
	}

	public GeneralFunction simplify() {
		return new Logb(function1.simplify(), function2.simplify());
	}


	public BinaryFunction me(GeneralFunction function1, GeneralFunction function2) {
		return new Logb(function1, function2);
	}

}
