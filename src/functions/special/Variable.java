package functions.special;

import functions.GeneralFunction;

import java.util.*;


public class Variable extends SpecialFunction {

	/**
	 * The character representing this variable
	 */
	public final String varID;

	/**
	 * Constructs a new {@link Variable}
	 * @param varID The variable's representative character
	 */
	public Variable(String varID) {
		this.varID = varID;
	}


	public String toString() {
		return varID;
	}


	public GeneralFunction getDerivative(String varID) {
		return new Constant(this.varID.equals(varID) ? 1 : 0);
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
