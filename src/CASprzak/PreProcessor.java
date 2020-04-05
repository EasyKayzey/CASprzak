package CASprzak;

import CASprzak.SpecialFunctions.Constant;

import java.util.ArrayList;
import java.util.Stack;

public class PreProcessor {
	public static final String[] operations = {"^", "*", "/", "+", "-", "logb", "log", "ln", "sqrt", "exp", "sinh", "cosh", "tanh"};
	public static final String[] operationsTrig = {"sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan"};


	public PreProcessor() {
	}


	private int getPrecedence(String input) {
		if (input.equals("^")) return 4;
		if (input.equals("*") || input.equals("/")) return 3;
		if (input.equals("+") || input.equals("-")) return 2;
		if (input.equals("(")) return 0;
		return 5;
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
		String[] tokens = infix.split("((?!\\w)(?<=\\w)(?<!$)|(?<!\\w)((?<!\\W-)|(?<=\\)-))(?<!^-)(?<!$)(?<!^)|(?=\\())(?<!\\.)((?!\\.)|(?=\\.)(?<!\\d))(?!\\s+)(?<!\\s)|\\s+");
		ArrayList<String> postfix = new ArrayList<>();
		Stack<String> operators = new Stack<>();

		for (String token : tokens) {
			System.out.println(token);
			System.out.println(operators.toString());
			if (Constant.isSpecialConstant(token)) {
				postfix.add(token);
			} else if (isAnOperator(token)) {
				if (operators.empty()) {
					operators.push(token);
				} else if (getPrecedence(token) <= getPrecedence(operators.peek()) && !token.equals("^")) {
					postfix.add(operators.pop());
					operators.push(token);
				} else {
					operators.push(token);
				}
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
}
