package functions.unitary.transforms;

import functions.GeneralFunction;
import functions.unitary.UnitaryFunction;

import java.util.Map;

/**
 * A wrapper class used to store functions in the integration pipeline, allowing users to utilize the methods provided by the {@link functions.Differentiable} interface.
 */
public class PartialDerivative extends Transformation {
	/**
	 * Constructs a new {@link PartialDerivative}
	 * @param operand The operand on the {@link PartialDerivative}
	 * @param respectTo The variable that the {@link PartialDerivative} is with respect to
	 */
	public PartialDerivative(GeneralFunction operand, char respectTo) {
		super(operand, respectTo);
	}

	@Override
	public String toString() {
		return "d/d" + respectToChar + "[" + operand.toString() + "]";
	}

	@Override
	public UnitaryFunction clone() {
		return new PartialDerivative(operand.clone(), respectToChar);
	}

	@Override
	public GeneralFunction substituteVariable(char varID, GeneralFunction toReplace) {
		if (varID == respectToChar)
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
		return new PartialDerivative(operand.substituteVariable(varID, toReplace), respectToChar);
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof PartialDerivative pd)
			return respectToChar == pd.respectToChar && operand.equalsSimplified(pd.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(GeneralFunction that) {
		if (that instanceof PartialDerivative pd)
			if (respectToChar == pd.respectToChar)
				return operand.compareTo(pd.operand);
			else
				return respectToChar - pd.respectToChar;
		else
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf");
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		return new PartialDerivative(operand.getSimplifiedDerivative(varID), respectToChar);
	}

	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		return operand.getSimplifiedDerivative(respectToChar).evaluate(variableValues);
	}


	@Override
	public UnitaryFunction simplifyInternal() {
		return new PartialDerivative(operand.simplify(), respectToChar);
	}


	public UnitaryFunction getInstance(GeneralFunction function) {
		return new PartialDerivative(function, respectToChar);
	}

	/**
	 * Returns the partial derivative of the operand
	 * @return the partial derivative of the operand
	 */
	public GeneralFunction execute() {
		return operand.getSimplifiedDerivative(respectToChar);
	}
}
