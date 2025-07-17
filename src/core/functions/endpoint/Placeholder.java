package core.functions.endpoint;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.tools.ParsingTools;
import core.tools.exceptions.IllegalNameException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Placeholder extends EndpointFunction {

	public final String label;
	public final String[] variables;
	public final int[] derivatives;

	public Placeholder(String label, String... variables) {
		this(label, variables, new int[variables.length]);
	}

	public Placeholder(String label, String[] variables, int[] derivatives) {
		if (label == null || label.isEmpty())
			throw new IllegalNameException("Placeholder label cannot be null or empty");
		if (variables == null || variables.length == 0)
			throw new IllegalArgumentException("Placeholder variables cannot be null or empty");
		this.label = label;
		this.variables = variables;
		this.derivatives = derivatives;
	}

	public double evaluate(Map<String, Double> variableValues) {
		return Double.NaN;
	}

	public String toString() {
		// return label + "(" + String.join(", ", variables) + ")";
		StringBuilder sb = new StringBuilder();
		sb.append(label);
		if (Arrays.stream(derivatives).sum() > 0) {
			if (sb.charAt(sb.length() - 1) == '}') {
				sb.deleteCharAt(sb.length() - 1);
				sb.append(",");
			} else {
				sb.append("_{");
			}
			for (int i = 0; i < variables.length; i++) {
				for (int j = 0; j < derivatives[i]; j++) {
					sb.append(variables[i]);
				}
			}
			sb.append("}");
		}
		sb.append("(");
		for (int i = 0; i < variables.length; i++) {
			sb.append(variables[i]);
			if (i != variables.length - 1)
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}

	public Placeholder incrementDerivative(int index) {
		int[] newDerivatives = Arrays.copyOf(derivatives, derivatives.length);
		newDerivatives[index]++;
		return new Placeholder(label, variables, newDerivatives);
	}

	public GeneralFunction getDerivative(String varID) {
		for (int i = 0; i < variables.length; i++) {
			if (variables[i].equals(varID)) {
				return incrementDerivative(i);
			}
		}
		return new Constant(0);
	}

	public GeneralFunction clone() {
		return new Placeholder(label, variables, derivatives);
	}

	public GeneralFunction simplify() {
		return this;
	}

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test,
			Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		return this;
	}

	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof Placeholder other) {
			return this.label.equals(other.label)
					&& Arrays.equals(this.variables, other.variables)
					&& Arrays.equals(this.derivatives, other.derivatives);
		}
		return false;
	}

	@SuppressWarnings({ "ConstantConditions" })
	public int compareSelf(GeneralFunction that) {
		if (that instanceof Placeholder other)
			return (this.label + this.variables.toString() + this.derivatives.toString())
					.compareTo(other.label + other.variables.toString() + other.derivatives.toString());
		return -1;
	}

	public int hashCode() {
		return label.hashCode() + Arrays.hashCode(variables) + Arrays.hashCode(derivatives);
	}
}
