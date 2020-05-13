package functions.special;

import functions.GeneralFunction;

import java.util.*;


public class Variable extends SpecialFunction {

	/**
	 * The character representing this variable
	 */
	public final char varID;

	/**
	 * Constructs a new {@link Variable}
	 * @param varID The variable's representative character
	 */
	public Variable(char varID) {
		this.varID = varID;
	}


	public String toString() {
		return String.valueOf(varID);
	}


	public GeneralFunction getDerivative(char varID) {
		return new Constant(this.varID == varID ? 1 : 0);
	}

	public double evaluate(Map<Character, Double> variableValues) {
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
		return (that instanceof Variable variable) && (varID == variable.varID);
	}

	public int compareSelf(GeneralFunction that) {
		return this.varID - ((Variable) that).varID;
	}
}
