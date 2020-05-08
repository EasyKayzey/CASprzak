package functions.binary;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
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
	public GeneralFunction getDerivative(char varID) {
		if (function2 instanceof Constant base)
			return new Product(function1.getSimplifiedDerivative(varID), DefaultFunctions.reciprocal(new Product(new Ln(new Constant(base.constant)), function1)));
		else
			return new Product(new Sum(new Product(function1.getSimplifiedDerivative(varID), new Ln(function2), DefaultFunctions.reciprocal(function1)), new Product(DefaultFunctions.NEGATIVE_ONE, function2.getSimplifiedDerivative(varID), new Ln(function1), DefaultFunctions.reciprocal(function2))), new Pow(DefaultFunctions.NEGATIVE_TWO, new Ln(function2)));
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
	}//TODO this needs to incorporate the new simplifies


	public BinaryFunction me(GeneralFunction function1, GeneralFunction function2) {
		return new Logb(function1, function2);
	}

	public GeneralFunction toSpecialCase() {
		return new Product(new Ln(function1), DefaultFunctions.reciprocal(new Ln(function2)));
	}


	@Override
	public String toString() {
		return "(log_{" + function2.toString() + "}(" + function1.toString() + "))";
	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	public GeneralFunction simplifyPowers() {
		if (function1 instanceof Pow power)
			return new Product(power.function1, new Logb(power.function2, function2).simplifyIdentity());
		else if (function1 instanceof Exp exp)
			return new Product(exp.operand, new Logb(function2, DefaultFunctions.E));
		else
			return this;
	}

	public GeneralFunction simplifyIdentity() {
		if (function2.equals(function1))
			return DefaultFunctions.ONE;
		else
			return this;
	}
}
