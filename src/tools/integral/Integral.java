package tools.integral;

import functions.Function;
import functions.commutative.Add;
import functions.special.Constant;

import java.util.Map;

public class Integral extends Function {
	public final Function integrand;
	public final char respectTo;

	public Integral(Function integrand, char respectTo) {
		this.integrand = integrand;
		this.respectTo = respectTo;
	}

	@Override
	public String toString() {
		return "∫[" + integrand.toString() + "]d" + respectTo;
	}

	@Override
	public Function clone() {
		return new Integral(integrand.clone(), respectTo);
	}

	@Override
	public Function substitute(char varID, Function toReplace) {
		return new Integral(integrand.substitute(varID, toReplace), respectTo);
	}

	@Override
	public boolean equals(Function that) {
		if (that instanceof Integral integral)
			return respectTo == integral.respectTo && integrand.equals(integral.integrand);
		else
			return false;
	}

	@Override
	protected int compareSelf(Function that) {
		if (that instanceof Integral integral) {
			if (respectTo == integral.respectTo)
				return integrand.compareTo(integral.integrand);
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

	public Function integrate() {
		if (integrand instanceof Add terms) {
			Function[] integratedTerms = new Function[terms.getFunctionsLength()];
			for(int i = 0; i < terms.getFunctionsLength(); i++) {
				integratedTerms[i] = new Integral(terms.getFunctions()[i], respectTo);
			}
			return new Add(integratedTerms);
		}
		return new Constant(0);
	}
}
