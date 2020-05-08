package parsing;

import java.util.LinkedList;
import java.util.List;

public class OperationLists {
	/**
	 * A list of unitary operations
	 */
	public static final List<String> unitaryOperations = new LinkedList<>() {
		{
			add("-");
			add("/");
			add("!");
			add("\\sin");
			add("\\cos");
			add("\\tan");
			add("\\log");
			add("\\ln");
			add("\\sqrt");
			add("\\exp");
			add("\\abs");
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
			add("\\logb");
			add("\\frac");
			add("C");
			add("P");
		}
	};
}
