package functions.endpoint;

import config.Settings;
import functions.GeneralFunction;
import tools.ParsingTools;
import tools.exceptions.IllegalNameException;

import java.util.Map;
import java.util.NoSuchElementException;


public class Variable extends EndpointFunction {

	/**
	 * The String representing this variable
	 */
	public final String varID;

	/**
	 * Constructs a new {@link Variable}
	 * @param varID The variable's representative String
	 */
	public Variable(String varID) {
		if (Settings.enforcePatternMatchingNames && !ParsingTools.validNames.matcher(varID).matches())
			throw new IllegalNameException(varID);
		this.varID = varID;
	}


	public String toString() {
		return ParsingTools.processEscapes(varID);
	}


	public GeneralFunction getDerivative(String varID) {
		return new Constant(varID.equals(this.varID) ? 1 : 0);
	}

	public double evaluate(Map<String, Double> variableValues) {
		if (!variableValues.containsKey(varID))
			throw new NoSuchElementException("No value was assigned to variable " + varID + ".");
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
