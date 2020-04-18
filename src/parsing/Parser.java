package parsing;

import functions.Function;
import functions.special.Constant;
import functions.special.Variable;

import java.util.Stack;

public class Parser {
	public static final String[] binaryOperations = {"^", "*", "+", "logb"};
	public static final String[] unitaryOperations = {"-", "/", "sin", "cos", "tan", "log", "ln", "sqrt", "exp", "abs", "sign", "dirac", "sin", "cos", "tan", "csc", "sec", "cot", "asin", "acos", "atan", "acsc", "asec", "acot", "sinh", "cosh", "tanh", "csch", "sech", "coth", "asinh", "acosh", "atanh", "acsch", "asech", "acoth"};

	private static char[] variables = {'x', 'y', 'z'};

	private Parser(){}

	public static void setVariables(char... variables) {
		Parser.variables = variables;
	}

	public static boolean isUnitaryOperator(String input) {
		for (String x : unitaryOperations) {
			if (x.equals(input)) return true;
		}
		return false;
	}

	public static boolean isBinaryOperator(String input) {
		for (String x : binaryOperations) {
			if (x.equals(input)) return true;
		}
		return false;
	}

	/**
	 * @param variable the character corresponding to the variable
	 * @return the ID of the variable, used internally
	 * @throws IndexOutOfBoundsException if no such variable exists
	 */
	public static int getVarID(char variable) throws IndexOutOfBoundsException {
		for (int i = 0; i < variables.length; i++)
			if (variables[i] == variable) {
				return i;
			}
		throw new IndexOutOfBoundsException("No variable " + variable + " found.");
	}

	public static Function parse(String infix) {
		PreProcessor.setVariables(variables);
		Variable.setVarNames(variables);
		return Parser.parse(PreProcessor.toPostfix(infix));
	}

	public static Function parse(String[] postfix) {
		Stack<Function> functionStack = new Stack<>();
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
					functionStack.push(FunctionMaker.variable(getVarID(variableName)));
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
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size());
		return functionStack.pop();
	}

}
