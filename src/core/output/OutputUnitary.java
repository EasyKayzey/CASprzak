package core.output;

import java.util.Collection;
import java.util.List;

/**
 * Provides a parent class for all {@link OutputFunction}s of one argument
 */
public class OutputUnitary implements OutputFunction {

	/**
	 * The name of the function
	 */
	protected final String functionName;

	/**
	 * The operand of the function
	 */
	protected final OutputFunction operand;

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operand the operand of the function
	 */
	public OutputUnitary(String functionName, OutputFunction operand) {
		this.functionName = functionName;
		this.operand = operand;
	}

	public String getName() {
		return functionName;
	}

	public Collection<OutputFunction> getOperands() {
		return List.of(operand);
	}

	public String toString() {
		return functionName + "(" + operand.toString() + ")";
	}

	public String toLatex() {
		return functionName + " \\left( " + operand.toLatex() + " \\right) ";
	}

	public boolean equals(Object that) {
		if (that instanceof OutputUnitary other)
			return functionName.equals(other.functionName) && operand.equals(other.operand);
		else
			return false;
	}

	public int hashCode() {
		return operand.hashCode() + 7 * functionName.hashCode();
	}

}
