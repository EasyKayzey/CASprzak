package parsing;

import functions.GeneralFunction;
import functions.special.Constant;
import tools.MiscTools;

import java.lang.reflect.MalformedParametersException;
import java.util.Deque;
import java.util.LinkedList;

public class Parser {
	/**
	 * A list of unitary operations
	 */
	public static final String[] unitaryOperations = {"-", "/", "!", "sin", "cos", "tan", "log", "ln", "sqrt", "exp", "abs", "sign", "dirac", "sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan", "acsc", "asec", "acot", "sinh", "cosh", "tanh", "csch", "sech", "coth", "asinh", "acosh", "atanh", "acsch", "asech", "acoth"};

	/**
	 * A list of binary operations
	 */
	public static final String[] binaryOperations = {"^", "*", "+", "logb", "C", "P"};

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
	 * @return a {@link GeneralFunction} corresponding to the infix string
	 */
	public static GeneralFunction parse(String infix) {
		return Parser.parse(PreProcessor.toPostfix(infix));
	}

	/**
	 * Parses infix using {@link parsing.PreProcessor} and {@link #parse(String[])}, then simplifies the output
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string, simplified
	 */
	public static GeneralFunction parseSimplified(String infix) {
		return parse(infix).simplify();
	}

	/**
	 * Parses an array of postfix tokens into a {@link GeneralFunction}
	 * @param postfix array of tokens in postfix
	 * @return a {@link GeneralFunction} corresponding to the postfix string
	 */
	public static GeneralFunction parse(String[] postfix) {
		Deque<GeneralFunction> functionStack = new LinkedList<>();
		for (String token : postfix) {
			if (Constant.isSpecialConstant(token)) {
				functionStack.push(new Constant(token));
			} else if (!isUnitaryOperator(token) && !isBinaryOperator(token)) {
				if (Constant.isSpecialConstant(token)) return FunctionMaker.specialConstant(token);
				try {
					functionStack.push(FunctionMaker.constant(Double.parseDouble(token)));
				} catch (Exception e) {
					char variableName = MiscTools.getCharacter(token);
					functionStack.push(FunctionMaker.variable(variableName));
				}
			} else if (isBinaryOperator(token)) {
				GeneralFunction a = functionStack.pop();
				GeneralFunction b = functionStack.pop();
				functionStack.push(FunctionMaker.makeBinary(token, a, b));
			} else if (isUnitaryOperator(token)) {
				GeneralFunction c = functionStack.pop();
				functionStack.push(FunctionMaker.makeUnitary(token, c));
			}
		}
		if (functionStack.size() != 1)
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size() + ", current stack is " +functionStack);
		return functionStack.pop();
	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	public static GeneralFunction toFunction(Object input) {
		if (input instanceof GeneralFunction f)
			return f;
		else if (input instanceof Double d)
			return new Constant(d);
		else if (input instanceof String s)
			return parse(s);
		else
			throw new MalformedParametersException("Cannot parse " + input);
	}

	/**
	 * Evaluates infix corresponding to a constant, like {@code pi/3}
	 * @param infix infix string of constant
	 * @return a double corresponding to the evaluated constant to be evaluated
	 */
	public static double getConstant(String infix) {
		return parse(infix).evaluate(null);
	}

}
