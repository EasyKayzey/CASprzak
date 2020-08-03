package functions.unitary.transforms;

import functions.Evaluable;
import functions.GeneralFunction;
import functions.endpoint.Variable;
import functions.unitary.UnitaryFunction;
import output.OutputFunction;
import output.OutputString;
import output.OutputUnitary;
import tools.exceptions.DerivativeDoesNotExistException;

import java.util.Map;

/**
 * The {@link Differential} class is used as an intermediary for operations related to parsing derivatives and integrals.
 * Operations such as {@link #execute} and {@link Evaluable#evaluate(Map)} are NOT SUPPORTED, as all instances of this class should be converted to other transforms before evaluation.
 * If evaluation or execution of this class is ever attempted by the CAS, please raise an issue on the <a href="https://github.com/EasyKayzey/CASprzak/">GitHub repository</a>.
 */
public class Differential extends Transformation {

	/**
	 * Constructs a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives
	 * @param respectTo the variable that the differential is with respect to
	 */
	public Differential(String respectTo) {
		super(new Variable(respectTo), respectTo);
	}

	/**
	 * Constructs a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives, using a {@link Variable}
	 * @param operand the variable that the differential is with respect to
	 */
	public Differential(Variable operand) {
		this(operand.varID);
	}

	/**
	 * Constructs a new {@link Differential}, which is sometimes used as an intermediary for integrals and derivatives, assuming that the operand is a variable {@link Variable}
	 * @param variable the variable that the differential is with respect to
	 * @throws ClassCastException if the operand is not an instance of {@link Variable}
	 */
	public Differential(GeneralFunction variable) {
		this((Variable) variable);
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
	public GeneralFunction substituteVariables(Map<String, ? extends GeneralFunction> toSubstitute) {
		if (toSubstitute.containsKey(respectTo))
			throw new UnsupportedOperationException("You cannot substitute the variable you are working with respect to.");
		return this;
	}

	@Override
	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof Differential diff)
			return respectTo.equals(diff.respectTo) && operand.equalsSimplified(diff.operand);
		else
			return false;
	}

	@Override
	public int compareSelf(GeneralFunction that) {
		if (that instanceof Differential diff)
			if (respectTo.equals(diff.respectTo))
				return operand.compareTo(diff.operand);
			else
				return respectTo.compareTo(diff.respectTo);
		else
			throw new IllegalStateException("Comparing a " + this.getClass().getSimpleName() + " with a " + that.getClass().getSimpleName() + " using compareSelf.");
	}

	/**
	 * Evaluation is not supported by this class, as it is purely an intermediary
	 * @return nothing, because this method will always throw an error
	 * @throws DerivativeDoesNotExistException whenever this method is called
	 * @param varID The variable that the function's derivative is taken with respect to
	 */
	@Override
	public GeneralFunction getDerivative(String varID) {
		throw new DerivativeDoesNotExistException(this);
	}

	/**
	 * Differentiation is not supported by this class, as it is purely an intermediary
	 * @return nothing, because this method will always throw an error
	 * @throws UnsupportedOperationException whenever this method is called
	 * @param variableValues The values of each variable
	 */
	@Override
	public double evaluate(Map<String, Double> variableValues) {
		throw new UnsupportedOperationException("Cannot evaluate a differential " + this + ".");
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
			throw new IllegalArgumentException("Cannot create a differential with respect to non-variable function " + function + ".");
		return new Differential((Variable) function);
	}

	/**
	 * Execution is not supported by this class, as it is purely an intermediary
	 * @return nothing, because this method will always throw an error
	 * @throws UnsupportedOperationException whenever this method is called
	 */
	public GeneralFunction execute() {
		throw new UnsupportedOperationException("Cannot execute a differential " + this + ".");
	}

	public OutputFunction toOutputFunction() {
		return new OutputDifferential(respectTo);
	}

	private static class OutputDifferential extends OutputUnitary {

		public OutputDifferential(String respectTo) {
			super("differential", new OutputString(respectTo));
		}

		@Override
		public String toString() {
			return "d" + operand;
		}

		@Override
		public String toLatex() {
			return " d" + operand;
		}

	}
}
