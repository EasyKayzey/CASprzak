package parsing;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InfixTokenizer {
	private static final Pattern absoluteValueEnd = Pattern.compile(
			"\\|(?=([^|]*\\|[^|]*\\|)*" +							// Ensures an even number of following absolute value signs
			"[^|]*$)"												// Allows for any number of following non-bar characters and asserts EOL position
	);
	private static final Pattern absoluteValueStart = Pattern.compile(
			"(?<=\\w)\\|"											// Only matches if preceded by a word character so '*abs(' can be substituted
	);
	private static final Pattern adjacentMultiplier = Pattern.compile(
			"(?<=\\d)(?=[a-zA-Z])(?![ECP])" +						// Matches if preceded by a digit and followed by a non-ECP letter
			"|(?<=[a-zA-Z])(?<![ECP])(?=[\\d])" +					// Matches if preceded by a non-ECP letter and followed by a digit
			"|(?<=\\))(?=[\\w(])" + 								// Matches if preceded by ) and followed by a word character or (
			"|(?<=[a-zA-Z)])(?=\\.)" +								// Matches if preceded by a letter or ) and followed by a dot
			"|(?<=[\\d)])(?=\\()(?<!logb_\\d)"						// Matches if preceded by [a digit not preceded by logb_] or ) and followed by (
	);
	private static final Pattern subtractionFinder = Pattern.compile(
			"(?<!^)(?<!E)" +										// Ensures not preceded by newline or E
			"(?<![\\^\\-+*/\\s({])\\s*-"							// Ensures not preceded by an operation or (, then matches - signs and all spaces preceding
	);
	private static final Pattern division = Pattern.compile(
			"(?<![^a-zA-Z]d)/"										// Slashes, as long as they're not preceded by a non-alpha then a d
	);
	private static final Pattern openParen = Pattern.compile("\\(");
	private static final Pattern closeParen = Pattern.compile("\\)");
	private static final Pattern plus = Pattern.compile("\\+");
	private static final Pattern times = Pattern.compile("\\*");
	private static final Pattern infixSplitter = Pattern.compile(
			"\\s+|(?<!\\s)(" + 										// Splits on spaces, and ensured spaces are not including in following splits
			"(?<=!)|(?=!)" +										// Splits if preceded or followed by !
			"|(?<!\\s)(?=\\\\)" +									// Splits if followed by a LaTeX escape
			"|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))" +				// Splits if preceded by non-word and followed by word and not [preceded by - and followed by a digit]
			"|(?<=\\w)(?=\\W)((?<!E)|(?!-)))" +						// Splits if preceded by a word and followed by a non-word, unless [the word was E and the non-word was -]
			"|(?<=[()])|(?=[()]))" +								// Splits if preceded or followed by a parenthesis
			"(?<![ .\\\\])(?![ .])" +								// The PREVIOUS FOUR LINES ONLY WORK if not preceded or followed by a dot or space, and not preceded by a LaTeX escape
			"|(?<=[CP])|(?=[CP])" +									// Splits if preceded or followed by C or P
			"|(?<=[^\\x00-\\x7F])|(?=[^\\x00-\\x7F])" +				// Splits if preceded or followed by a non-ASCII character
			"|(?<=[A-Za-z(])(?=\\.))"								// Splits if preceded by a letter or open parenthesis and followed by a dot
	);
	private static final Pattern characterPairsToMultiply = Pattern.compile(
			"(?<!\\\\[a-zA-Z]{0,15})" +								// Ensures that the character is not LaTeX-escaped (up to 15 characters)
					"(?<![CEP])(?![CEP])" +							// Ensures the spaces before and after C, E, and P are not matched
					"(?<!logb_\\w)" +								// Ensures not preceded by logb
					"((?<!\\d)|(?!\\d))" +							// Ensures that spaces both preceded and followed by a digit are not matched
					"((?<=[a-zA-Z)\\d])|(?<=[^\\x00-\\x7F]))" +		// Preceded by a digit, alphabetic char, or non-ascii character
					"\\s*" + 										// Allows for spaces
					"((?=[a-zA-Z\\\\(\\d])|(?=[^\\x00-\\x7F]))" 	// Followed by a digit, alphabetic char, or non-ascii character
	);
	private static final Pattern differential = Pattern.compile(
			"d(?=[a-zA-Z\\x00-\\x7F])"
	);

	private InfixTokenizer(){}

	/**
	 * Tokenizes an input infix string into a format supported by the {@link FunctionParser}
	 * @param infix input string in infix
	 * @return array of infix tokens
	 */
	public static List<String> tokenizeInfix(String infix) {
		// Make absolute values into unitary functions
		infix = absoluteValueStart.matcher(absoluteValueEnd.matcher(infix).replaceAll(")")).replaceAll("*\\abs(").replace("|", " \\abs(");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = adjacentMultiplier.matcher(infix).replaceAll(" * ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = subtractionFinder.matcher(division.matcher(infix).replaceAll("*/")).replaceAll("+-");
		// Turns differentials like dx into \d x
		infix = differential.matcher(infix).replaceAll("\\d ");
		// Turns expressions like xyz into x*y*z
		infix = characterPairsToMultiply.matcher(infix).replaceAll(" * ");
		// Replace curly braces and underscores with parentheses and spaces
		infix = infix.replace("{", "(").replace("}", ")").replace("_", " ");
		// Adds parentheses to enforce order of operations
		infix = "((((" + times.matcher(plus.matcher(closeParen.matcher(openParen.matcher(infix).replaceAll("((((")).replaceAll("))))")).replaceAll("))+((")).replaceAll(")*(") + "))))";
		// Splits infix into tokens
		return infixSplitter.splitAsStream(infix).collect(Collectors.toCollection(LinkedList::new));
	}
}
