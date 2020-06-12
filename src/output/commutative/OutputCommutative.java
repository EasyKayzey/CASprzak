package output.commutative;

import output.OutputFunction;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OutputCommutative implements OutputFunction {

	protected final String functionName;
	protected final List<OutputFunction> operands;

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

	public String toAscii() {
		return operands.stream()
				.map(OutputFunction::toAscii)
				.collect(Collectors.joining(", ", functionName + "(", ")"));
	}

}
