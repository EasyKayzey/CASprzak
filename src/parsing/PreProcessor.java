package parsing;

import functions.special.Constant;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class PreProcessor {

	private PreProcessor(){}

	/**
	 * Turns an infix string into a postfix array
	 * @param infix input string in infix
	 * @return array of postfix tokens
	 */
	public static List<String> toPostfix(String infix) {
		List<String> tokens = InfixTokenizer.tokenizeInfix(infix);
		Deque<String> postfix = new LinkedList<>();
		Deque<String> operators = new LinkedList<>();

		for (String token : tokens) {
			if (Constant.isSpecialConstant(token)) {
				postfix.add(token);
			} else if ("(".equals(token)) {
				operators.push(token);
			} else if (")".equals(token)) {
				while (!"(".equals(operators.peek()))
					postfix.add(operators.pop());
				operators.pop();
			} else if (Parser.unitaryOperations.contains(token) || Parser.binaryOperations.contains(token)) {
				operators.push(token);
			} else {
				postfix.add(token);
			}
		}

		while (operators.size() != 0)
			postfix.add(operators.pop());

		return new ArrayList<>(postfix);
	}

}
