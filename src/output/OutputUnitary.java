package output;

import java.util.Collection;
import java.util.List;

public class OutputUnitary implements OutputFunction {

	private final String functionName;
	private final OutputFunction operand;

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

}
