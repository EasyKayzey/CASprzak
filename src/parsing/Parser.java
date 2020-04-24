package parsing;

import functions.Function;
import functions.special.Constant;
import functions.special.Variable;

import java.util.Deque;
import java.util.LinkedList;

public class Parser {
	/**
	 * A list of unitary operations
	 */
	public static final String[] unitaryOperations = {"-", "/", "sin", "cos", "tan", "log", "ln", "sqrt", "exp", "abs", "sign", "dirac", "sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan", "acsc", "asec", "acot", "sinh", "cosh", "tanh", "csch", "sech", "coth", "asinh", "acosh", "atanh", "acsch", "asech", "acoth"};

	/**
	 * A list of binary operations
	 */
	public static final String[] binaryOperations = {"^", "*", "+", "logb"};

	private Parser(){}

	/**
	 * Checks if a string is in {@link #unitaryOperations}
	 * @param input operation
	 * @return true if unitary
	 */
	public static boolean isUnitaryOperator(String input) {
		for (String x : unitaryOperations) {
			if (x.equals(input)) return true;
		}
		return false;
	}

	/**
	 * Checks if a string is in {@link #binaryOperations}
	 * @param input operation
	 * @return true if binary
	 */
	public static boolean isBinaryOperator(String input) {
		for (String x : binaryOperations) {
			if (x.equals(input)) return true;
		}
		return false;
	}


	/**
	 * Parses infix using {@link parsing.PreProcessor} and {@link #parse(String[])}
	 * @param infix infix string
	 * @return a {@link functions.Function} corresponding to the infix string
	 */
	public static Function parse(String infix) {
		return Parser.parse(PreProcessor.toPostfix(infix));
	}

	/**
	 * Parses an array of postfix tokens into a {@link functions.Function}
	 * @param postfix array of tokens in postfix
	 * @return a {@link functions.Function} corresponding to the postfix string
	 */
	public static Function parse(String[] postfix) {
		Deque<Function> functionStack = new LinkedList<>();
		for (String token : postfix) {
			if (Constant.isSpecialConstant(token)) {
				functionStack.push(new Constant(token));
			} else if (!isUnitaryOperator(token) && !isBinaryOperator(token)) {
				if (Constant.isSpecialConstant(token)) return FunctionMaker.specialConstant(token);
				try {
					functionStack.push(FunctionMaker.constant(Double.parseDouble(token)));
				} catch (Exception e) {
					if (token.length() > 1)
						throw new IllegalArgumentException(token + " is not a valid function.");
					char variableName = token.charAt(0);
					functionStack.push(FunctionMaker.variable(Variable.getVarID(variableName)));
				}
			} else if (isBinaryOperator(token)) {
				Function a = functionStack.pop();
				Function b = functionStack.pop();
				functionStack.push(FunctionMaker.makeBinary(token, a, b));
			} else if (isUnitaryOperator(token)) {
				Function c = functionStack.pop();
				functionStack.push(FunctionMaker.makeUnitary(token, c));
			}
		}
		if (functionStack.size() != 1)
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size() + ", current stack is " +functionStack);
		return functionStack.pop();
	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	public static Function toFunction(Object input) {
		if (input instanceof Function f)
			return f;
		else if (input instanceof Double d)
			return new Constant(d);
		else
			return null;
	}
}
