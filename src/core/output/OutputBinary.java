package core.output;

import java.util.Collection;
import java.util.List;

/**
 * Provides a parent class for all {@link OutputFunction}s of two arguments
 */
public class OutputBinary implements OutputFunction {

	/**
	 * The name of the function
	 */
	protected final String functionName;

	/**
	 * The first operand of the function
	 */
	protected final OutputFunction first;

	/**
	 * The second operand of the function
	 */
	protected final OutputFunction second;

	/**
	 * Returns a new {@code OutputBinary} function
	 * @param functionName the name of the function
	 * @param first the first operand of the function
	 * @param second the second operand of the function
	 */
	public OutputBinary(String functionName, OutputFunction first, OutputFunction second) {
		this.functionName = functionName;
		this.first = first;
		this.second = second;
	}

	public String getName() {
		return functionName;
	}

	public Collection<OutputFunction> getOperands() {
		return List.of(first, second);
	}

	public String toString() {
		return functionName + "(" + first.toString() + ", " + second.toString() + ")";
	}

	public String toLatex() {
		return functionName + "\\left( " + first.toLatex() + " , " + second.toLatex() + " \\right)";
	}

	public boolean equals(Object that) {
		if (that instanceof OutputBinary other)
			return functionName.equals(other.functionName) && first.equals(other.first) && second.equals(other.second);
		else
			return false;
	}

	public int hashCode() {
		return first.hashCode() + 7 * second.hashCode() + 31 * functionName.hashCode();
	}

}
