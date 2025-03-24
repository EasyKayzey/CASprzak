package core.functions.endpoint;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.tools.ParsingTools;
import core.tools.exceptions.IllegalNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class Placeholder extends EndpointFunction {

	public final String label;
	public final String[] variables;

	public Placeholder(String label, String... variables) {
		if (label == null || label.isEmpty())
			throw new IllegalNameException("Placeholder label cannot be null or empty");
		if (variables == null || variables.length == 0)
			throw new IllegalArgumentException("Placeholder variables cannot be null or empty");
		this.label = label;
		this.variables = variables;
	}

	public double evaluate(Map<String, Double> variableValues) {
		Constant.addSpecialConstant(label, Double.NaN);
		return Constant.getSpecialConstant(label);
	}

	public String toString() {
		return label;
	}

	public GeneralFunction getDerivative(String varID) {
		for (String variable : variables)
			if (variable.equals(varID))
				return new Placeholder("d_" + varID + "[" + label + "]", variables);
		return new Constant(0);
	}

	public GeneralFunction clone() {
		return new Placeholder(label, variables);
	}

	public GeneralFunction simplify() {
		return this;
	}

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test,
			Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		return this;
	}

	public boolean equalsFunction(GeneralFunction that) {
		if (that instanceof Placeholder)
			return this.label.equals(((Placeholder) that).label);
		return false;
	}

	@SuppressWarnings({ "ConstantConditions" })
	public int compareSelf(GeneralFunction that) {
		if (that instanceof Placeholder)
			return this.label.compareTo(((Placeholder) that).label);
		return -1;
	}

	public int hashCode() {
		return label.hashCode();
	}
}
