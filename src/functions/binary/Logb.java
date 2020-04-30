package functions.binary;

import functions.Function;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.unitary.Ln;

import java.util.Map;

public class Logb extends BinaryFunction {
	/**
	 * Constructs a new Logb
	 * @param argument The argument of the logarithm
	 * @param base The base of the logarithm
	 */
	public Logb(Function argument, Function base) {
		super(argument, base);
	}


	@Override
	public String toString() {
		return "log_{" + function2.toString() + "}(" + function1.toString() + ")";
	}

	@Override
	public Function getDerivative(char varID) {
		if (function2 instanceof Constant base)
			return new Product(function1.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Product(new Ln(new Constant(base.constant)), function1)));
		else
			return new Product(new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2), new Pow(new Constant(-1), function1)), new Product(new Constant(-1), function2.getSimplifiedDerivative(varID), new Ln(function1), new Pow(new Constant(-1), function2))), new Pow(new Constant(-2), new Ln(function2)));
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
	}

	public Function clone() {
		return new Logb(function1.clone(), function2.clone());
	}

	public Function simplify() {
		return new Logb(function1.simplify(), function2.simplify());
	}


	public BinaryFunction me(Function function1, Function function2) {
		return new Logb(function1, function2);
	}

}
