package tools;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import parsing.FunctionParser;
import parsing.LatexReplacer;

import java.lang.reflect.MalformedParametersException;

public class ParsingTools {
	/**
	 * If the input is a {@link GeneralFunction}, returns the input. If the input is a {@code Double}, returns a new {@link Constant} of that value. If the input is a {@link String}, parses it with {@link FunctionParser#parseInfix(String)}.
	 * @param input the input to be parsed as described above
	 * @return the parsed input as described above
	 */
	@SuppressWarnings("ChainOfInstanceofChecks")
	public static GeneralFunction toFunction(Object input) {
		if (input instanceof GeneralFunction f)
			return f;
		else if (input instanceof Double d)
			return new Constant(d);
		else if (input instanceof String s)
			return FunctionParser.parseInfix(s);
		else
			throw new MalformedParametersException("Cannot parse " + input + " of type " + input.getClass().getSimpleName() + ".");
	}

	/**
	 * Evaluates infix corresponding to a constant, such as {@code pi/3}
	 * @param infix the infix string of the constant
	 * @return a {@code double} corresponding to the evaluated constant to be evaluated
	 */
	public static double getConstant(String infix) {
		return FunctionParser.parseInfix(infix).evaluate(null);
	}

	/**
	 * Parses a string to a boolean using the following rules, ignoring case:
	 * TRUE: {@code true, t, 1, yes, y}
	 * FALSE: {@code false, f, 0, no, n}
	 * @param s the string to be parsed
	 * @return the string parsed to a boolean
	 */
	public static boolean parseBoolean(String s) {
		return switch (s.toLowerCase()) {
			case "true", "t", "1", "yes", "y" -> true;
			case "false", "f", "0", "no", "n" -> false;
			default -> throw new MalformedParametersException(s + " cannot be parsed to a boolean");
		};
	}

	/**
	 * Converts a {@code double} within {@link Settings#integerMargin} of an integer to an {@code int}
	 * @param d the {@code double} to be converted
	 * @return the {@code double} rounded to an integer
	 * @throws IllegalArgumentException if the {@code double} is not within {@link Settings#integerMargin} of an integer
	 */
	public static int toInteger(double d) throws IllegalArgumentException{
		if (isAlmostInteger(d))
			return (int) (d + .5);
		else
			throw new IllegalArgumentException("Double " + d + " is not within " + Settings.zeroMargin + " of an integer.");
	}

	/**
	 * Checks if a {@code double} is within Settings.integerMargin of an integer
	 * @param d the {@code double} to be checked
	 * @return true if the {@code double} is within {@link Settings#integerMargin} of an integer
	 */
	public static boolean isAlmostInteger(double d) throws IllegalArgumentException{
		return Math.abs(((int) (d + .5)) - d) < Settings.integerMargin;
	}

	/**
     * Converts the string to a {@code char}, supporting legitimate single-character strings like {@code "x"}, LaTeX-escaped characters like {@code "\epsilon"}, and single-quote-surrounded characters like {@code "'x'"}.
     * @param input the string containing the character
     * @return the character represented by the string
     * @throws IllegalArgumentException if the input is not in one of the formats specified above
     */
    public static char getCharacter(String input) { // TODO should we remove support for single-quote-surrounded characters?
    	if (!Settings.enforceEscapes)
    		input = LatexReplacer.addEscapes(input);
    	input = LatexReplacer.encodeGreek(input);
        if (input.length() == 1)
            return input.charAt(0);
        else if (input.length() == 3 && input.charAt(0) == '\'' && input.charAt(2) == '\'')
            return input.charAt(1);
        else
            throw new IllegalArgumentException("Input length should be 1 for FunctionParser.toCharacter, input given was " + input);
    }
}
