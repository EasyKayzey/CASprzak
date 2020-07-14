package output;

import java.util.Collection;
import java.util.List;

public class OutputParenthesizer implements OutputFunction {

	protected final OutputFunction operand;

	/**
	 * Returns a new {@code OutputParenthesizer}
	 * @param operand the function to be parenthesized
	 */
	public OutputParenthesizer(OutputFunction operand) {
		this.operand = operand;
	}

	public String getName() {
		return "parens";
	}

	public Collection<OutputFunction> getOperands() {
		return List.of(operand);
	}

	public String toString() {
		return "(" + operand.toString() + ")";
	}

	public String toLatex() {
		return " \\left( " + operand.toLatex() + " \\right) ";
	}

	public boolean equals(Object that) {
		if (that instanceof OutputParenthesizer other)
			return this.operand.equals(other.operand);
		else
			return false;
	}

	public int hashCode() {
		return operand.hashCode() + 7 * "()".hashCode();
	}
}
