package parsing;

import functions.GeneralFunction;
import functions.special.Constant;
import functions.special.Variable;
import tools.MiscTools;

import java.lang.reflect.MalformedParametersException;
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
					functionStack.push(new Variable(MiscTools.getCharacter(token)));
				}
			}
		}

		if (functionStack.size() != 1)
			throw new IndexOutOfBoundsException("functionStack size is " + functionStack.size() + ", current stack is " + functionStack);

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
			throw new MalformedParametersException("Cannot parse " + input + " of type " + input.getClass().getSimpleName() + ".");
	}

	/**
	 * Evaluates infix corresponding to a constant, like {@code pi/3}
	 * @param infix infix string of constant
	 * @return a double corresponding to the evaluated constant to be evaluated
	 */
	public static double getConstant(String infix) {
		return parse(infix).evaluate(null);
	} // TODO consolidate all of these to one class (from MiscTools as well)

	/**
	 * A list of unitary operations
	 */
	public static final List<String> unitaryOperations = new LinkedList<>() {
		{
			add("-");
			add("/");
			add("!");
			add("sin");
			add("cos");
			add("tan");
			add("log");
			add("ln");
			add("sqrt");
			add("exp");
			add("abs");
			add("sign");
			add("dirac");
			add("sin");
			add("cos");
			add("tan");
			add("csc");
			add("sec");
			add("cot");
			add("asin");
			add("acos");
			add("atan");
			add("acsc");
			add("asec");
			add("acot");
			add("sinh");
			add("cosh");
			add("tanh");
			add("csch");
			add("sech");
			add("coth");
			add("asinh");
			add("acosh");
			add("atanh");
			add("acsch");
			add("asech");
			add("acoth");
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
			add("logb");
			add("C");
			add("P");
		}
	};
}
