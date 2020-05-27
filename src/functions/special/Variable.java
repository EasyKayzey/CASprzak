package functions.special;

import functions.GeneralFunction;

import java.util.*;


public class Variable extends SpecialFunction {

	/**
	 * The character representing this variable
	 */
	public final char varIDChar;

	/**
	 * Constructs a new {@link Variable}
	 * @param varID The variable's representative character
	 */
	public Variable(char varID) {
		this.varIDChar = varID;
	}


	public String toString() {
		return String.valueOf(varIDChar);
	}


	public GeneralFunction getDerivative(char varID) {
		return new Constant(this.varIDChar == varID ? 1 : 0);
	}

	public double evaluate(Map<Character, Double> variableValues) {
		if (!variableValues.containsKey(varIDChar))
			throw new NoSuchElementException("No value was assigned to variable " + varIDChar);
		else
			return variableValues.get(varIDChar);
	}


	public GeneralFunction clone() {
		return new Variable(varIDChar);
	}

	public GeneralFunction simplify() {
		return clone();
	}


	public boolean equalsFunction(GeneralFunction that) {
		return (that instanceof Variable variable) && (varIDChar == variable.varIDChar);
	}

	public int compareSelf(GeneralFunction that) {
		return this.varIDChar - ((Variable) that).varIDChar;
	}

	public int hashCode() {
		return Character.hashCode(varIDChar);
	}
}
