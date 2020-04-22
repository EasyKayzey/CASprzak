package functions.special;

import core.Settings;
import functions.Function;

import java.util.Arrays;
import java.util.List;

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
