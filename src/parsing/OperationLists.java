package parsing;

import java.util.LinkedList;
import java.util.List;

/**
 * {@link OperationLists} is a centralized location for the strings of all unitary and binary operations to be stored for parsing.
 * The conversions between these strings and their {@link functions.GeneralFunction} equivalents are held in {@link FunctionMaker}.
 */
public class OperationLists {

	private OperationLists(){}

	/**
	 * A list of unitary operations
	 */
	public static final List<String> unitaryOperations = new LinkedList<>() {
		{
			add("-");
			add("/");
			add("!");
			add("\\ln");
			add("\\int");
			add("\\log");
			add("\\exp");
			add("\\abs");
			add("\\difn");
			add("\\sqrt");
			add("\\sign");
			add("\\dirac");
			add("\\sin");
			add("\\cos");
			add("\\tan");
			add("\\csc");
			add("\\sec");
			add("\\cot");
			add("\\asin");
			add("\\acos");
			add("\\atan");
			add("\\acsc");
			add("\\asec");
			add("\\acot");
			add("\\sinh");
			add("\\cosh");
			add("\\tanh");
			add("\\csch");
			add("\\sech");
			add("\\coth");
			add("\\asinh");
			add("\\acosh");
			add("\\atanh");
			add("\\acsch");
			add("\\asech");
			add("\\acoth");
		}
	};

	/**
	 * A list of binary operations
	 */
	public static final List<String> binaryOperations = new LinkedList<>() {
		{
			add("^");
			add("*");
			add("+");
			add("C");
			add("P");
			add("\\pd");
			add("\\logb");
			add("\\frac");
		}
	};
}
