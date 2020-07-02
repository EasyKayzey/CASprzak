package functions.special;

import functions.GeneralFunction;
import tools.exceptions.IllegalNameException;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;


public class Variable extends SpecialFunction {

	/**
	 * The String representing this variable
	 */
	public final String varID;

	/**
	 * This Pattern matches all valid variable and function names
	 */
	public static final Pattern validVariables = Pattern.compile("[a-zA-Z[^\\x00-\\x7F]]|\\\\[a-zA-Z[^\\x00-\\x7F]][\\w.'[^\\x00-\\x7F]]*");

	/**
	 * Constructs a new {@link Variable}
	 * @param varID The variable's representative String
	 */
	public Variable(String varID) {
		if (!validVariables.matcher(varID).matches())
			throw new IllegalNameException(varID);
		if (varID.charAt(0) == '\\')
			varID = varID.substring(1);
		this.varID = varID;
	}


	public String toString() {
		return varID;
	}


	public GeneralFunction getDerivative(String varID) {
		return new Constant(varID.equals(this.varID) ? 1 : 0);
	}

	public double evaluate(Map<String, Double> variableValues) {
		if (!variableValues.containsKey(varID))
			throw new NoSuchElementException("No value was assigned to variable " + varID);
		else
			return variableValues.get(varID);
	}


	public GeneralFunction clone() {
		return new Variable(varID);
	}

	public GeneralFunction simplify() {
		return clone();
	}


	public boolean equalsFunction(GeneralFunction that) {
		return (that instanceof Variable variable) && (varID.equals(variable.varID));
	}

	public int compareSelf(GeneralFunction that) {
		return this.varID.compareTo(((Variable) that).varID);
	}

	public int hashCode() {
		return varID.hashCode();
	}
}
