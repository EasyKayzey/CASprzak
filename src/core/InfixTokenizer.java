package core;

import java.util.regex.Pattern;

public class InfixTokenizer {
	private static final Pattern absoluteValueEnd = Pattern.compile("\\|(?=([^|]*\\|[^|]*\\|)*$)");
	private static final Pattern absoluteValueStart = Pattern.compile("(?<=\\w)\\|");
	private static final Pattern adjacentMultiplier = Pattern.compile("(?<=\\d)(?=[a-zA-Z])|(?<=[a-zA-Z])(?=[\\d])|(?<=\\))(?=[\\w(])|(?<=[\\d)])(?=\\()(?<!logb_\\d)");
	private static final Pattern subtractionFinder = Pattern.compile("(?<!^)(?<![\\^\\-+*/ ])\\s*-");
	private static final Pattern OOO1 = Pattern.compile("\\(");
	private static final Pattern OOO2 = Pattern.compile("\\)");
	private static final Pattern OOO3 = Pattern.compile("\\+");
	private static final Pattern OOO4 = Pattern.compile("\\*");

	/**
	 * Tokenizes an input infix string into a format supported by the {@link PreProcessor}
	 * @param infix input string in infix
	 * @return array of infix tokens
	 */
	public static String[] tokenizeInfix(String infix, char[] variables) {
		// Remove LaTeX escapes
		infix = infix.replace("\\","");
		// Make absolute values into unitary functions
		infix = absoluteValueStart.matcher(absoluteValueEnd.matcher(infix).replaceAll(")")).replaceAll("*abs(").replace("|"," abs(");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = adjacentMultiplier.matcher(infix).replaceAll(" * ");
		// Replace curly braces and underscores with parentheses and spaces
		infix = infix.replace("{","(").replace("}",")").replace("_"," ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = subtractionFinder.matcher(infix).replaceAll("+-").replace("/","*/");
		// Turns expressions like xyz into x*y*z
		infix = parseVariablePairs(infix, variables);
		// Adds parentheses to enforce order of operations
		infix = "((((" + OOO4.matcher(OOO3.matcher(OOO2.matcher(OOO1.matcher(infix).replaceAll("((((")).replaceAll("))))")).replaceAll("))+((")).replaceAll(")*(") + "))))";
		// Splits infix into tokens
		return infix.split("\\s+|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))|(?<=\\w)(?=\\W))|(?<=[()])|(?=[()]))(?<![ .])(?![ .])");
	}

	/**
	 * Turns pairs of variables like xy into x*y
	 * @param infix input string in infix
	 * @return infix string with inserted asterisks
	 */
	private static String parseVariablePairs(String infix, char[] variables) {
		for (char a : variables) {
			for (char b : variables) {
				infix = infix.replaceAll("(?<="+a+")\\s*(?="+b+")","*");
			}
		}
		return infix;
	}
}
