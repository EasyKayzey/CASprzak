package functions.special;

import functions.GeneralFunction;

import java.util.*;


public class Variable extends SpecialFunction {
	/**
	 * The list containing all the variable characters
	 */
	public static List<Character> variables = new ArrayList<>() {
		{
			add('x');
			add('y');
			add('z');
		}
	}; //TODO implement sortVariables here and in KeywordInterface

	private static int variablesEnd = 3; // TODO possible remove FunctionVariables

	/**
	 * The character representing this variable
	 */
	public final char varID;

	/**
	 * Constructs a new Variable
	 * @param varID The variable's representative character
	 */
	public Variable(char varID) {
		this.varID = varID;
	}

	/**
	 * Sets the variable names
	 * @param variables array of variable names
	 */
	public static void setVariablesArray(Character... variables) {
		Variable.variables = new ArrayList<>(Arrays.asList(variables));
	}


	public static void addVariable(char variable) {
		if (!variables.contains(variable)) {
			variables.add(variablesEnd, variable);
			variablesEnd++;
		}
	}

	public static void removeVariable(char variable) {
		variables.remove(variable);
		variablesEnd--;
	}

	public static void addFunctionVariable(char variable) {
		variables.add(variable);
	}

	public static void removeFunctionVariable(char variable) {
		variables.remove(variable);
	}

	public static void clearVariables() {
		variables.clear();
		variablesEnd = 0;
	}

	public static void clearFunctionVariables() {
		variables = variables.subList(0, variablesEnd);
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
