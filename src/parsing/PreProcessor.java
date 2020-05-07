package parsing;

import config.Settings;
import functions.special.Constant;

import java.util.*;

public class PreProcessor {

	private PreProcessor(){}

	/**
	 * Turns an infix string into a postfix array
	 * @param infix input string in infix
	 * @return array of postfix tokens
	 */
	public static List<String> toPostfix(String infix) { // TODO add unicode support
		infix = LatexReplacer.encodeGreek(infix);
		if (!Settings.enforceEscapes)
			infix = LatexReplacer.addEscapes(infix);
		List<String> tokens = InfixTokenizer.tokenizeInfix(infix);
		Deque<String> postfix = new LinkedList<>();
		Deque<String> operators = new LinkedList<>();
		System.out.println(tokens);

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
