package functions.unitary.transforms;

import functions.Function;
import functions.unitary.UnitaryFunction;

import java.util.Map;

public class PartialDerivative extends TransformFunction {
	/**
	 * Constructs a new Integral
	 * @param operand The operand on the PartialDerivative
	 * @param respectTo The variable that the PartialDerivative is with respect to
	 */
	public PartialDerivative(Function operand, char respectTo) {
		super(operand, respectTo);
	}

	@Override
	public String toString() {
		return "d/d" + respectTo + "[" + operand.toString() + "]";
	}

	@Override
	public UnitaryFunction clone() {
		return new PartialDerivative(operand.clone(), respectTo);
	}

	@Override
	public UnitaryFunction substitute(char varID, Function toReplace) {
		if (varID == respectTo)
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
		return new PartialDerivative(operand.substitute(varID, toReplace), respectTo);
	}

	@Override
	public boolean equalsFunction(Function that) {
		if (that instanceof PartialDerivative pd)
			return respectTo == pd.respectTo && operand.equals(pd.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(Function that) {
		if (that instanceof PartialDerivative pd) {
			if (respectTo == pd.respectTo)
				return operand.compareTo(pd.operand);
			else
				return respectTo - pd.respectTo;
		} else {
			return 1;
		}
	}

	@Override
	public Function getDerivative(char varID) {
		return new PartialDerivative(operand.getSimplifiedDerivative(varID), respectTo);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return operand.getSimplifiedDerivative(respectTo).evaluate(variableValues);
	}

	@Override
	public Function simplify() {
		return simplifyInternal();
	}

	@Override
	public UnitaryFunction simplifyInternal() {
		return new PartialDerivative(operand.simplify(), respectTo);
	}


	public UnitaryFunction me(Function function) {
		return new PartialDerivative(function, respectTo);
	}
}
