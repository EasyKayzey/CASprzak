package output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OutputCommutative implements OutputFunction {

	protected final String functionName;
	protected final Collector<CharSequence, ?, String> joiningCollector;
	protected final List<OutputFunction> operands;

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operands the operands of the function
	 */
	public OutputCommutative(String functionName, List<OutputFunction> operands) {
		this.functionName = functionName;
		this.operands = operands;
		this.joiningCollector =  Collectors.joining(", ", functionName + "(", ")");
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

	public String toString() {
		return operands.stream()
				.map(OutputFunction::toString)
				.collect(joiningCollector);
	}

	public String toLatex() {
		return toString();
	}

	public boolean equals(Object that) {
		if (that instanceof OutputCommutative other)
			return functionName.equals(other.functionName) && operands.equals(other.operands);
		else
			return false;
	}

	public int hashCode() {
		return operands.hashCode() + 7 * functionName.hashCode();
	}

}