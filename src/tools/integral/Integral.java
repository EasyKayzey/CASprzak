package tools.integral;

import functions.Function;
import functions.commutative.Add;
import functions.special.Constant;
import functions.unitary.UnitaryFunction;

import java.util.Map;

public class Integral extends UnitaryFunction {
	public final char respectTo;

	public Integral(Function integrand, char respectTo) {
		super(integrand);
		this.respectTo = respectTo;
	}

	@Override
	public String toString() {
		return "∫[" + function.toString() + "]d" + respectTo;
	}

	@Override
	public UnitaryFunction clone() {
		return new Integral(function.clone(), respectTo);
	}

	@Override
	public UnitaryFunction substitute(char varID, Function toReplace) {
		return new Integral(function.substitute(varID, toReplace), respectTo);
	}

	@Override
	public boolean equals(Function that) {
		if (that instanceof Integral integral)
			return respectTo == integral.respectTo && integrand.equals(integral.integrand);
		else
			return false;
	}

	@Override
	public int compareSelf(Function that) {
		if (that instanceof Integral integral) {
			if (respectTo == integral.respectTo)
				return function.compareTo(integral.integrand);
			else
				return respectTo - integral.respectTo;
		} else {
			return 1;
		}
	}

	@Override
	public Function getDerivative(char varID) {
		return null; //TODO implement
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return 0; //TODO implement
	}

	@Override
	public Function simplify() {
		return integrate(); //TODO implement
	}

	@Override
	public UnitaryFunction me(Function function) {
		return new Integral(function, respectTo);
	}

	public Function integrate() {
		if (function instanceof Add terms) {
			Function[] integratedTerms = new Function[terms.getFunctionsLength()];
			for(int i = 0; i < terms.getFunctionsLength(); i++) {
				integratedTerms[i] = new Integral(terms.getFunctions()[i], respectTo);
			}
			return new Add(integratedTerms);
		}
		return new Constant(0);
	}
}
