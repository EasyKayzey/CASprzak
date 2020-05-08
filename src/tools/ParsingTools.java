package tools;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import parsing.LatexReplacer;
import parsing.Parser;

import java.lang.reflect.MalformedParametersException;

public class ParsingTools { // TODO review this file
	@SuppressWarnings("ChainOfInstanceofChecks")
	public static GeneralFunction toFunction(Object input) {
		if (input instanceof GeneralFunction f)
			return f;
		else if (input instanceof Double d)
			return new Constant(d);
		else if (input instanceof String s)
			return Parser.parse(s);
		else
			throw new MalformedParametersException("Cannot parse " + input + " of type " + input.getClass().getSimpleName() + ".");
	}

	/**
	 * Evaluates infix corresponding to a constant, like {@code pi/3}
	 * @param infix infix string of constant
	 * @return a double corresponding to the evaluated constant to be evaluated
	 */
	public static double getConstant(String infix) {
		return Parser.parse(infix).evaluate(null);
	}

	/**
	 * Parses a string to a boolean using the following rules, ignoring case:
	 * TRUE:  true, t, 1, yes, y
	 * FALSE: false, f, 0, no, n
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
	 * Converts a double within {@link Settings#integerMargin} of an integer to an integer
	 * @param d the double to be converted
	 * @return the double rounded to an integer
	 * @throws IllegalArgumentException if the double is not within {@link Settings#integerMargin} of an integer
	 */
	public static int toInteger(double d) throws IllegalArgumentException{
		if (isAlmostInteger(d))
			return (int) (d + .5);
		else
			throw new IllegalArgumentException("Double " + d + " is not within " + Settings.zeroMargin + " of an integer.");
	}

	/**
	 * Checks if a double is within Settings.integerMargin of an integer
	 * @param d the double to be converted
	 * @return true if the double is within {@link Settings#integerMargin} of an integer
	 */
	public static boolean isAlmostInteger(double d) throws IllegalArgumentException{
		return Math.abs(((int) (d + .5)) - d) < Settings.integerMargin;
	}

	/**
     * Converts the string to a character,
     * @param input the string containing the character
     * @return the character in the string
     * @throws IllegalArgumentException if the input is not one character or three characters in the format 'c'
     */
    public static char getCharacter(String input) {
    	if (!Settings.enforceEscapes)
    		input = LatexReplacer.addEscapes(input);
    	input = LatexReplacer.encodeGreek(input);
        if (input.length() == 1)
            return input.charAt(0);
        else if (input.length() == 3 && input.charAt(0) == '\'' && input.charAt(2) == '\'')
            return input.charAt(1);
        else
            throw new IllegalArgumentException("Input length should be 1 for Parser.toCharacter, input given was " + input);
    }
}
