package functions.special;

import core.Settings;
import functions.Function;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Variable extends Function {
	/**
	 * The array containing all the variable characters
	 */
	public static List<Character> variables = Arrays.asList('x', 'y', 'z');

	/**
	 * The index of this variable in {@link #variables}
	 */
	private final int varID;

	/**
	 * Constructs a new Variable
	 * @param varID The variable's ID
	 */
	public Variable(int varID) {
		this.varID = varID;
	}

	/**
	 * Sets the variable names
	 * @param variables array of variable names
	 */
	public static void setVariables(Character... variables) {
		Variable.variables = Arrays.asList(variables);
	}

	/** @param variable the character corresponding to the variable
	 * @return the ID of the variable, used internally
	 * @throws IndexOutOfBoundsException if no such variable exists
	 */
	public static int getVarID(char variable) throws IndexOutOfBoundsException {
		ListIterator<Character> iter = variables.listIterator();
		while (iter.hasNext()) {
			if (iter.next() == variable) {
				return iter.previousIndex();
			}
		}
		throw new IndexOutOfBoundsException("No variable " + variable + " found.");
	}

	public static void addVariable(char variable) {
		variables.add(variable);
	}


	public String toString() {
		return String.valueOf(variables.get(varID));
	}


	public Function getDerivative(int varID) {
		return new Constant((this.varID == varID ? 1 : 0));
	}

	public double evaluate(double... variableValues) {
		return variableValues[varID];
	}

	public Function clone() {
		return new Variable(varID);
	}

	public Function simplify() {
		return clone();
	}


	public Function substitute(int varID, Function toReplace) {
		if (this.varID == varID)
			return toReplace;
		else if (Settings.trustImmutability)
			return this;
		else
			return clone();
	}


	public boolean equals(Function that) {
		return (that instanceof Variable) && (varID == ((Variable) that).varID);
	}

	public int compareSelf(Function that) {
		return this.varID - ((Variable) that).varID;
	}
}
