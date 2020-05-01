package functions.unitary;

import config.Settings;
import functions.Function;
import functions.commutative.Sum;
import tools.integration.StageOne;

import java.util.Map;

public class Integral extends UnitaryFunction {
	public final char respectTo;

	public Integral(Function integrand, char respectTo) {
		super(integrand);
		this.respectTo = respectTo;
	}

	@Override
	public String toString() {
		return "∫[" + operand.toString() + "]d" + respectTo;
	}

	@Override
	public UnitaryFunction clone() {
		return new Integral(operand.clone(), respectTo);
	}

	@Override
	public UnitaryFunction substitute(char varID, Function toReplace) {
		return new Integral(operand.substitute(varID, toReplace), respectTo);
	}

	@Override
	public boolean equalsFunction(Function that) {
		if (that instanceof Integral integral)
			return respectTo == integral.respectTo && operand.equals(integral.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(Function that) {
		if (that instanceof Integral integral) {
			if (respectTo == integral.respectTo)
				return operand.compareTo(integral.operand);
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
	public UnitaryFunction simplifyInternal() {
		if(Settings.trustImmutability)
			return this;
		else
			return clone();
	}

	@Override
	public UnitaryFunction me(Function function) {
		return new Integral(function, respectTo);
	}

	public Function integrate() {
		if (operand instanceof Sum terms) {
			Function[] integratedTerms = new Function[terms.getFunctionsLength()];
			for(int i = 0; i < terms.getFunctionsLength(); i++) {
				integratedTerms[i] = new Integral(terms.getFunctions()[i], respectTo);
			}
			return new Sum(integratedTerms);
		}
		return StageOne.derivativeDivides(operand, respectTo);
	}
}
