package parsing;

import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;
import tools.ParsingTools;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Parser {

	private Parser(){}

	/**
	 * Parses infix using {@link parsing.PreProcessor} and {@link #parse(List)}
	 * @param infix infix string
	 * @return a {@link GeneralFunction} corresponding to the infix string
	 */
	public static GeneralFunction parse(String infix) {
		return Parser.parse(PreProcessor.toPostfix(infix));
	}

	/**
	 * Parses infix using {@link parsing.PreProcessor} and {@link #parse(List)}, then simplifies the output
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
	public static GeneralFunction parse(List<String> postfix) {
		Deque<GeneralFunction> functionStack = new LinkedList<>();

		for (String token : postfix) {
			if (Constant.isSpecialConstant(token)) {
				functionStack.push(new Constant(token));
			} else if (binaryOperations.contains(token)) {
				GeneralFunction a = functionStack.pop();
				GeneralFunction b = functionStack.pop();
				functionStack.push(FunctionMaker.makeBinary(token, a, b));
			} else if (unitaryOperations.contains(token)) {
				GeneralFunction c = functionStack.pop();
				functionStack.push(FunctionMaker.makeUnitary(token, c));
			} else {
				try {
					functionStack.push(new Constant(Double.parseDouble(token)));
				} catch (NumberFormatException e) {
					functionStack.push(new Variable(ParsingTools.getCharacter(token)));
				}
			}
		}

		if (functionStack.size() != 1)
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size() + ", current stack is " + functionStack);

		return functionStack.pop();
	}

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
			add("C");
			add("P");
		}
	};
}
