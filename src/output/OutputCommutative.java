package output;

import java.util.Collection;
import java.util.List;

public class OutputCommutative implements OutputFunction {

	private final String functionName;
	private final List<OutputFunction> operands;

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operands the operands of the function
	 */
	public OutputCommutative(String functionName, List<OutputFunction> operands) {
		this.functionName = functionName;
		this.operands = operands;
	}

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operands the operands of the function
	 */
	public OutputCommutative(String functionName, OutputFunction... operands) {
		this(functionName, List.of(operands));
	}

	public String getName() {
		return functionName;
	}

	public Collection<OutputFunction> getOperands() {
		return operands;
	}

}
