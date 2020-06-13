package output.unitary;

import output.OutputFunction;

import java.util.Collection;
import java.util.List;

public class OutputUnitary implements OutputFunction {

	protected final String functionName;
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

	public String toAscii() {
		return functionName + "(" + operand.toAscii() + ")";
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
