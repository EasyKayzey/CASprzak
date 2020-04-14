package CASprzak;

import CASprzak.SpecialFunctions.Constant;

import java.util.ArrayList;
import java.util.Stack;

public class PreProcessor {
	public static final String[] operations = {"^", "*", "/", "+", "-", "logb", "log", "ln", "sqrt", "exp", "sinh", "cosh", "tanh"};
	public static final String[] operationsTrig = {"sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan"};
	protected static char[] variables;


	public PreProcessor(char[] variables) {
		PreProcessor.variables = variables;
	}


	public boolean isAnOperator(String input) {
		for (String x : operations) {
			if (x.equals(input)) return true;
		}
		for (String x : operationsTrig) {
			if (x.equals(input)) return true;
		}
		return false;
	}

	public String[] toPostfix(String infix) {
		// Remove LaTeX escapes
		infix = infix.replace("\\","");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = infix.replaceAll("(?<=\\d)(?=[a-zA-Z])|(?<=[a-zA-Z])(?=[\\d])|(?<=\\))(?=[\\w(])|(?<=[\\d)])(?=\\()(?<!logb_\\d)", " * ");
		// Replace curly braces and underscores with parentheses and spaces
		infix = infix.replace("{","(").replace("}",")").replace("_"," ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = infix.replaceAll("(?<!^)(?<![\\^\\-+*/ ])\\s*-","+-").replace("/","*/");
		// Turns expressions like x y z into x*y*z
		infix = parseVariablePairs(infix);
		// Adds parentheses to enforce order of operations
		infix = "((((" + infix.replaceAll("\\(","((((").replaceAll("\\)","))))").replaceAll("\\+","))+((").replaceAll("\\*",")*(")+ "))))";


		String[] tokens = infix.split("\\s+|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))|(?<=\\w)(?=\\W))|(?<=[()])|(?=[()]))(?<![ .])(?![ .])");
		ArrayList<String> postfix = new ArrayList<>();
		Stack<String> operators = new Stack<>();

		for (String token : tokens) {
			if (Constant.isSpecialConstant(token)) {
				postfix.add(token);
			} else if (isAnOperator(token)) {
				operators.push(token);
			} else if (token.equals("(")) {
				operators.push(token);
			} else if (token.equals(")")) {
				while (!operators.peek().equals("(")) {
					postfix.add(operators.pop());
				}
				operators.pop();
			} else {
				postfix.add(token);
			}
		}

		while (operators.size() != 0) {
			postfix.add(operators.pop());
		}

		return postfix.toArray(new String[0]);
	}

	private String parseVariablePairs(String infix) {
		for (char a : variables) {
			for (char b : variables) {
				infix = infix.replaceAll("(?<="+a+")\\s*(?="+b+")","*");
			}
		}
		return infix;
	}
}
