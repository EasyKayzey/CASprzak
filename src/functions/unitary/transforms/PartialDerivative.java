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
	public PartialDerivative(GeneralFunction operand, String respectTo) {
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
	public GeneralFunction substituteVariable(Map<String, ? extends GeneralFunction> toSubstitute) {
		if (toSubstitute.containsKey(respectTo))
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
		return new PartialDerivative(operand.substituteVariable(toSubstitute), respectTo);
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof PartialDerivative pd)
			return respectTo.equals(pd.respectTo) && operand.equalsSimplified(pd.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(GeneralFunction that) {
		if (that instanceof PartialDerivative pd)
			if (respectTo.equals(pd.respectTo))
				return operand.compareTo(pd.operand);
			else
				return respectTo.compareTo(pd.respectTo);
		else
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf");
	}

	@Override
	public GeneralFunction getDerivative(String varID) {
		return new PartialDerivative(operand.getSimplifiedDerivative(varID), respectTo);
	}

	@Override
	public double evaluate(Map<String, Double> variableValues) {
		return operand.getSimplifiedDerivative(respectTo).evaluate(variableValues);
	}


	@Override
	public UnitaryFunction simplifyInternal() {
		return new PartialDerivative(operand.simplify(), respectTo);
	}


	public UnitaryFunction getInstance(GeneralFunction function) {
		return new PartialDerivative(function, respectTo);
	}

	/**
	 * Returns the partial derivative of the operand
	 * @return the partial derivative of the operand
	 */
	public GeneralFunction execute() {
		return operand.getSimplifiedDerivative(respectTo);
	}
}
