package output.binary;

import output.OutputFunction;

import java.util.Collection;
import java.util.List;

public class OutputBinary implements OutputFunction {

	protected final String functionName;
	protected final OutputFunction first;
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

	public String toAscii() {
		return functionName + "(" + first.toAscii() + ", " + second.toAscii() + ")";
	}

}
