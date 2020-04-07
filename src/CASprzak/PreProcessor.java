package CASprzak;

import CASprzak.SpecialFunctions.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class PreProcessor {
	public static final String[] operations = {"^", "*", "/", "+", "-", "logb", "log", "ln", "sqrt", "exp", "sinh", "cosh", "tanh"};
	public static final String[] operationsTrig = {"sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan"};


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
		infix = infix.replace("{","(").replace("}",")").replace("\\","").replace("_"," ");
		infix = infix.replaceAll("(?<!^)(?<![\\^\\-+*/ ])\\s*-","+-").replace("/","*/");
		infix = "((((" + infix.replaceAll("\\(","((((").replaceAll("\\)","))))").replaceAll("\\+","))+((").replaceAll("\\*",")*(")+ "))))";
		String[] tokens = infix.split("\\s+|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))|(?<=\\w)(?=\\W))|(?<=[()])|(?=[()]))(?<![ .])(?![ .])");
//		System.out.println(Arrays.toString(tokens));
		ArrayList<String> postfix = new ArrayList<>();
		Stack<String> operators = new Stack<>();

		for (String token : tokens) {
//			System.out.println(token);
//			System.out.println(operators.toString());
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
}
