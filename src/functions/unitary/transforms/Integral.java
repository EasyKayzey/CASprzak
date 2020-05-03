package functions.unitary.transforms;

import config.Settings;
import functions.Function;
import functions.unitary.UnitaryFunction;
import tools.integration.StageOne;
import tools.singlevariable.NumericalIntegration;

import java.util.HashMap;
import java.util.Map;

public class Integral extends TransformFunction {
	/**
	 * Constructs a new Integral
	 * @param integrand The integrand on the Integral
	 * @param respectTo The variable that the Integral is with respect to
	 */
	public Integral(Function integrand, char respectTo) {
		super(integrand, respectTo);
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
		if (varID == respectTo)
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
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
		if (varID == respectTo)
			return operand;
		else
			return new Integral(operand.getSimplifiedDerivative(varID), respectTo);
	}

	/**
	 * Integrates the operand numerically from 0 to the value specified in the HashMap corresponding to respectTo
	 * @param variableValues the values of the variables of the {@link Function} at the point
	 * @return the operand integrated numerically
	 */
	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		Map<Character, Double> newMap = new HashMap<>(variableValues);
		double bound = newMap.remove(respectTo);
		return NumericalIntegration.simpsonsRule(operand.setVariables(newMap), 0, bound);
	}

	@Override
	public Function simplify() {
		return clone();
	}

	@Override
	public UnitaryFunction simplifyInternal() {
		if(Settings.trustImmutability)
			return this;
		else
			return clone();
	}


	public UnitaryFunction me(Function function) {
		return new Integral(function, respectTo);
	}

	/**
	 * Returns the Integral of the integrand if it can be found.
	 * @return the Integral of the integrand
	 */
	public Function execute() {
		return StageOne.derivativeDivides(operand, respectTo).simplify();
	}
}
