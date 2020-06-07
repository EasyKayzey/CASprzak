package parsing;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;

import java.util.*;

import static parsing.OperationMaps.binaryOperations;
import static parsing.OperationMaps.unitaryOperations;

/**
 * {@link FunctionParser} provides the central methods to execute the conversion of a function from a user-inputted string to an instance of {@link GeneralFunction}.
 */
public class FunctionParser {

	private FunctionParser(){}

	/**
	 * Parses infix to a {@link GeneralFunction} using {@link #toPostfix(String)} and {@link #parsePostfix(List)}
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string
	 */
	public static GeneralFunction parseInfix(String infix) {
		return FunctionParser.parsePostfix(toPostfix(infix));
	}

	/**
	 * Parses infix using {@link #parseInfix(String)}, then simplifies the output
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string, simplified
	 */
	public static GeneralFunction parseSimplified(String infix) {
		return parseInfix(infix).simplify();
	}

	/**
	 * Parses an array of postfix tokens into a {@link GeneralFunction}
	 * @param postfix array of tokens in postfix
	 * @return a {@link GeneralFunction} corresponding to the postfix string
	 */
	public static GeneralFunction parsePostfix(List<String> postfix) {
		Deque<GeneralFunction> functionStack = new LinkedList<>();

		for (String token : postfix) {
			if (Constant.isSpecialConstant(token)) {
				functionStack.push(new Constant(token));
			} else if (binaryOperations.containsKey(token)) {
				if (functionStack.size() < 2)
					throw new NoSuchElementException("Tried to pop two elements for token '" + token + "', but not enough elements exist. Parsing postfix: " + postfix + ". Current stack: " + functionStack + ".");
				GeneralFunction a = functionStack.pop();
				GeneralFunction b = functionStack.pop();
				functionStack.push(binaryOperations.get(token).construct(b, a));
			} else if (unitaryOperations.containsKey(token)) {
				if (functionStack.size() < 1)
					throw new NoSuchElementException("Tried to pop an element for token '" + token + "', but the stack is empty. Parsing postfix: " + postfix + ".");
				GeneralFunction c = functionStack.pop();
				functionStack.push(unitaryOperations.get(token).construct(c));
			} else try {
				functionStack.push(new Constant(Double.parseDouble(token)));
			} catch (NumberFormatException e) {
				functionStack.push(new Variable(LatexReplacer.encodeAll(token)));
			}
		}

		if (functionStack.size() < 1)
			throw new IndexOutOfBoundsException("Stack is empty at end of parsing, so there is nothing to return. Parsed postfix: " + postfix + ".");
		else if (functionStack.size() > 1)
			throw new IndexOutOfBoundsException("Stack has more than one function at end of parsing, likely because of mismatched open parentheses. Parsed postfix: " + postfix + ". Current stack: " + functionStack + ".");
		else
			return functionStack.pop();
	}

	/**
	 * Turns an infix string into a postfix array of tokens
	 * @param infix input string in infix
	 * @return an array of postfix tokens
	 */
	public static List<String> toPostfix(String infix) {
		if (!Settings.enforceEscapes)
			infix = LatexReplacer.addEscapes(infix);
		infix = LatexReplacer.encodeGreek(infix);
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
					if (operators.size() > 0)
						postfix.add(operators.pop());
					else
						throw new IllegalArgumentException("Mismatched parentheses in infix; too many end parentheses. Raw infix: " + infix + ". Current parsed postfix: " + postfix + ". Remaining operators: " + operators + ".");
				if ("(".equals(operators.peek()))
					operators.pop();
				else
					throw new IllegalArgumentException("Mismatched parentheses in infix; too many end parentheses. Raw infix: " + infix + ". Current parsed postfix: " + postfix + ". Remaining operators: " + operators + ".");
			} else if (unitaryOperations.containsKey(token) || binaryOperations.containsKey(token)) {
				operators.push(token);
			} else {
				postfix.add(token);
			}
		}

		while (operators.size() != 0)
			if ("(".equals(operators.peek()))
				throw new IllegalArgumentException("Mismatched parentheses in infix; too many open parentheses. Raw infix: " + infix + ". Current parsed postfix: " + postfix + ". Remaining operators: " + operators + ".");
			else
				postfix.push(operators.pop());

		return new ArrayList<>(postfix);
	}
}
