package show.ezkz.casprzak.core.tools;

import show.ezkz.casprzak.core.config.Settings;

import java.lang.reflect.MalformedParametersException;
import java.util.regex.Pattern;

/**
 * The {@link ParsingTools} class contains methods related to parsing user input, and is used heavily by the CASprzak parsing library.
 */
public class ParsingTools {

	/**
	 * Matches newlines with supported carriage returns
	 */
	public static final Pattern newline = Pattern.compile("\\R");

	/**
	 * Matches all valid variable, function, and constant names
	 */
	public static final Pattern validNames = Pattern.compile("[a-zA-Z[^\\x00-\\x7F]]|\\\\" + (Settings.escapeNames ? "" : "?") + "[a-zA-Z[^\\x00-\\x7F]][\\w.'[^\\x00-\\x7F]]*");

	private ParsingTools(){}

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
			return (int) Math.round(d);
		else
			throw new IllegalArgumentException("Double " + d + " is not within " + Settings.integerMargin + " of an integer.");
	}

	/**
	 * Checks if a {@code double} is within Settings.integerMargin of an integer
	 * @param d the {@code double} to be checked
	 * @return true if the {@code double} is within {@link Settings#integerMargin} of an integer
	 */
	public static boolean isAlmostInteger(double d) throws IllegalArgumentException{
		return Math.abs(Math.round(d) - d) < Settings.integerMargin;
	}

	/**
	 * Returns the input string with LaTeX escapes removed (if they exist) if {@link Settings#removeEscapes} is enabled
	 * @param input the possibly-escaped string
	 * @return the input with escapes processed
	 */
	public static String processEscapes(String input) {
		if (Settings.removeEscapes && input.length() > 0 && input.charAt(0) == '\\')
			return input.substring(1);
		else
			return input;
	}
}
