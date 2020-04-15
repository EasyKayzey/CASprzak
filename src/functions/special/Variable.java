package functions.special;

import core.Settings;
import functions.Function;

public class Variable extends Function {
	protected static char[] varNames;

	private final int varID;

	public Variable(int varID, char... varNames) {
		this.varID = varID;
		Variable.varNames = varNames;
	}

	public Variable(int varID) {
		this.varID = varID;
	}

	/**
	 * Sets the variable names
	 *
	 * @param varNames array of variable names
	 */
	public static void setVarNames(char... varNames) {
		Variable.varNames = varNames;
	}

	/**
	 * Returns a String representation of the Function
	 *
	 * @return String representation of function
	 */
	public String toString() {
		return String.valueOf(varNames[varID]);
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
