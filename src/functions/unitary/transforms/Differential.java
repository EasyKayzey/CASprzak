package functions.unitary.transforms;

import functions.GeneralFunction;
import functions.special.Variable;
import functions.unitary.UnitaryFunction;

import java.util.Map;

public class Differential extends TransformFunction {

	/**
	 * Constructs a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives
	 * @param respectTo the variable that the differential is with respect to
	 */
	public Differential(char respectTo) {
		super(null, respectTo);
	}

	/**
	 * Constructs a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives, using a {@link Variable}
	 * @param operand the variable that the differential is with respect to
	 */
	public Differential(Variable operand) {
		this(operand.varID);
	}

	@Override
	public String toString() {
		return "d" + respectTo;
	}

	@Override
	public UnitaryFunction clone() {
		return new Differential(respectTo);
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


	public UnitaryFunction simplify() {
		return this;
	}

	@Override
	public UnitaryFunction simplifyInternal() {
		return this;
	}


	public UnitaryFunction getInstance(GeneralFunction function) {
		if (!(function instanceof Variable))
			throw new IllegalArgumentException("Cannot create a differential with respect to non-variable function " + function);
		return new Differential((Variable) function);
	}

	/**
	 * @exclude
	 */
	public GeneralFunction execute() {
		throw new UnsupportedOperationException("Cannot execute a differential " + this);
	}
}
