package output;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OutputCommutative implements OutputFunction {

	protected final String functionName;
	protected final Collector<CharSequence, ?, String> normalJoiningCollector;
	protected final Collector<CharSequence, ?, String> latexJoiningCollector;
	protected final List<OutputFunction> operands;

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operands the operands of the function
	 * @param normalJoiningCollector the joining collector for basic {@link #toString()}
	 * @param latexJoiningCollector the joining collector for {@link #toLatex()}
	 */
	public OutputCommutative(String functionName, List<OutputFunction> operands, Collector<CharSequence, ?, String> normalJoiningCollector, Collector<CharSequence, ?, String> latexJoiningCollector) {
		this.functionName = functionName;
		this.operands = operands;
		this.normalJoiningCollector = normalJoiningCollector;
		this.latexJoiningCollector = latexJoiningCollector;
	}

	/**
	 * Returns a new {@code OutputUnitary} function
	 * @param functionName the name of the function
	 * @param operands the operands of the function
	 */
	public OutputCommutative(String functionName, List<OutputFunction> operands) {
		this(
				functionName,
				operands,
				Collectors.joining(", ", functionName + "(", ")"),
				Collectors.joining(" , ", functionName + "\\left( ", " \\right)")
		);
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
				.collect(normalJoiningCollector);
	}

	public String toLatex() {
		return operands.stream()
				.map(OutputFunction::toLatex)
				.collect(latexJoiningCollector);
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
