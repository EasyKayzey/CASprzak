package functions.unitary.transforms;

import functions.GeneralFunction;
import functions.special.Variable;
import functions.unitary.UnitaryFunction;

import java.util.Map;

public class Differential extends TransformFunction {

	/**
	 * Returns a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives
	 * @param operand the operand of the {@link Differential}, which must be a variable
	 * @param respectTo the variable that the differential is with respect to, which must match the variable
	 */
	public Differential(GeneralFunction operand, char respectTo) {
		super(operand, respectTo);
		if (!(operand instanceof Variable))
			throw new UnsupportedOperationException("Created a differential with a non-variable operand " + operand);
	}

	@Override
	public String toString() {
		return "d" + respectTo;
	}

	@Override
	public UnitaryFunction clone() {
		return new Differential(operand.clone(), respectTo);
	}

	@Override
	public GeneralFunction substituteVariable(char varID, GeneralFunction toReplace) {
		if (varID == respectTo)
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to");
		return this;
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof Differential diff)
			return respectTo == diff.respectTo && operand.equals(diff.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(GeneralFunction that) {
		if (that instanceof Differential diff)
			if (respectTo == diff.respectTo)
				return operand.compareTo(diff.operand);
			else
				return respectTo - diff.respectTo;
		else
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf");
	}

	@Override
	public GeneralFunction getDerivative(char varID) {
		throw new UnsupportedOperationException("Cannot get the derivative of a differential " + this);
	}


	@Override
	public double evaluate(Map<Character, Double> variableValues) {
		throw new UnsupportedOperationException("Cannot evaluate a differential " + this);
	}


	@Override
	public UnitaryFunction simplifyInternal() {
		return this;
	}


	public UnitaryFunction getInstance(GeneralFunction function) {
		return new Differential(function, respectTo);
	}

	/**
	 * @exclude
	 */
	public GeneralFunction execute() {
		throw new UnsupportedOperationException("Cannot execute a differential " + this);
	}
}
