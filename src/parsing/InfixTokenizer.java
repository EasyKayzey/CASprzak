package parsing;

import functions.special.Variable;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InfixTokenizer {
	private static final Pattern absoluteValueEnd = Pattern.compile(
			"\\|(?=([^|]*\\|[^|]*\\|)*" +					// Ensures an even number of following absolute value signs
			"[^|]*$)"										// Allows for any number of following non-bar characters and asserts EOL position
	);
	private static final Pattern absoluteValueStart = Pattern.compile(
			"(?<=\\w)\\|"									// Only matches if preceded by a word character so '*abs(' can be substituted
	);
	private static final Pattern adjacentMultiplier = Pattern.compile(
			"(?<=\\d)(?=[a-zA-Z])(?![ECP])" +				// Matches if preceded by a digit and followed by a non-ECP letter
			"|(?<=[a-zA-Z])(?<![ECP])(?=[\\d])" +			// Matches if preceded by a non-ECP letter and followed by a digit
			"|(?<=\\))(?=[\\w(])" + 						// Matches if preceded by ) and followed by a word character or (
			"|(?<=[a-zA-Z)])(?=\\.)" +						// Matches if preceded by a letter or ) and followed by a dot
			"|(?<=[\\d)])(?=\\()(?<!logb_\\d)"				// Matches if preceded by [a digit not preceded by logb_] or ) and followed by (
	);
	private static final Pattern subtractionFinder = Pattern.compile(
			"(?<!^)(?<!E)" +								// Ensures not preceded by newline or E
			"(?<![\\^\\-+*/\\s(])\\s*-"						// Ensures not preceded by an operation or (, then matches - signs and all spaces preceding
	);
	private static final Pattern openParen = Pattern.compile("\\(");
	private static final Pattern closeParen = Pattern.compile("\\)");
	private static final Pattern plus = Pattern.compile("\\+");
	private static final Pattern times = Pattern.compile("\\*");
	private static final Pattern infixSplitter = Pattern.compile(
			"\\s+" + 										// Splits on spaces
			"|(?<=!)|(?=!)" +								// Splits if preceded or followed by !
			"|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))" +		// Splits if preceded by non-word and followed by word and not [preceded by - and followed by a digit]
			"|(?<=\\w)(?=\\W)(?<!(?<=E)(?=-)))" +			// Splits if preceded by a word and followed by a non-word, unless [the word was E and the non-word was -]
			"|(?<=[()])|(?=[()]))" +						// Splits if preceded or followed by a parenthesis
			"(?<![ .])(?![ .])" +							// The PREVIOUS FOUR LINES ONLY WORK if not preceded or followed by a dot or space
			"|(?<=[CP])|(?=[CP])" +							// Splits if preceded or followed by C or P
			"|(?<=[A-Za-z(\\-])(?=\\.)"						// Splits if preceded by [a letter, (, or -] and followed by a dot
	);

	private InfixTokenizer(){}

	/**
	 * Tokenizes an input infix string into a format supported by the {@link PreProcessor}
	 * @param infix input string in infix
	 * @return array of infix tokens
	 */
	public static List<String> tokenizeInfix(String infix) {
		// Remove LaTeX escapes
		infix = infix.replace("\\", "");
		// Make absolute values into unitary functions
		infix = absoluteValueStart.matcher(absoluteValueEnd.matcher(infix).replaceAll(")")).replaceAll("*abs(").replace("|", " abs(");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = adjacentMultiplier.matcher(infix).replaceAll(" * ");
		// Replace curly braces and underscores with parentheses and spaces
		infix = infix.replace("{", "(").replace("}", ")").replace("_", " ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = subtractionFinder.matcher(infix).replaceAll("+-").replace("/", "*/");
		// Turns expressions like xyz into x*y*z
		infix = parseVariablePairs(infix);
		System.out.println(infix);
		// Adds parentheses to enforce order of operations
		infix = "((((" + times.matcher(plus.matcher(closeParen.matcher(openParen.matcher(infix).replaceAll("((((")).replaceAll("))))")).replaceAll("))+((")).replaceAll(")*(") + "))))";
		// Splits infix into tokens
		List<String> tokens = infixSplitter.splitAsStream(infix).collect(Collectors.toCollection(LinkedList::new));
		// Move postfix operations like ! to infix
		movePostfix(tokens);
		System.out.println(tokens);
		// Return tokens
		return tokens;
	}

	/**
	 * Turns pairs of variables like xy into x*y
	 * @param infix input string in infix
	 * @return infix string with inserted asterisks
	 */
	private static String parseVariablePairs(String infix) {
		for (char a : Variable.variables) {
			for (char b : Variable.variables) {
				infix = infix.replaceAll("(?<=" + a + ")\\s*(?=" + b + ")", "*");
			}
		}
		return infix;
	}

	private static void movePostfix(List<String> tokens) {

	}
}
