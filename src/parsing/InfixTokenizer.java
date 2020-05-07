package parsing;

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
			"|(?=\\\\)" +									// Splits if followed by a LaTeX escape
			"|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))" +		// Splits if preceded by non-word and followed by word and not [preceded by - and followed by a digit]
			"|(?<=\\w)(?=\\W)(?<!(?<=E)(?=-)))" +			// Splits if preceded by a word and followed by a non-word, unless [the word was E and the non-word was -] //TODO maybe make this use the notation of prev line
			"|(?<=[()])|(?=[()]))" +						// Splits if preceded or followed by a parenthesis
			"(?<![ .\\\\])(?![ .])" +						// The PREVIOUS FOUR LINES ONLY WORK if not preceded or followed by a dot or space, and not preceded by a LaTeX escape
			"|(?<=[CP])|(?=[CP])" +							// Splits if preceded or followed by C or P
			"|(?<=[A-Za-z(\\-])(?=\\.)"						// Splits if preceded by [a letter, (, or -] and followed by a dot
	);
	private static final Pattern characterPairs = Pattern.compile( //TODO write LatexTest
			"(?<!\\\\[a-zA-Z]{0,15})" +						// Ensures that the character is not LaTeX-escaped (up to 15 characters)
			"(?<=[\\w)])(?=[\\w\\\\(])" + 					// Matches the empty space between any two non-escaped characters and/or parentheses
			"|(?=\\\\[a-zA-Z]{0,15})\\s+(?=[\\w(\\\\])"		// Matches any spaces between an escaped word and a character, escape, or parenthesis
	);

	private InfixTokenizer(){}

	/**
	 * Tokenizes an input infix string into a format supported by the {@link PreProcessor}
	 * @param infix input string in infix
	 * @return array of infix tokens
	 */
	public static List<String> tokenizeInfix(String infix) {
		// Make absolute values into unitary functions
		infix = absoluteValueStart.matcher(absoluteValueEnd.matcher(infix).replaceAll(")")).replaceAll("*\\abs(").replace("|", " abs(");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = adjacentMultiplier.matcher(infix).replaceAll(" * ");
		// Replace curly braces and underscores with parentheses and spaces
		infix = infix.replace("{", "(").replace("}", ")").replace("_", " ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = subtractionFinder.matcher(infix).replaceAll("+-").replace("/", "*/");
		// Turns expressions like xyz into x*y*z
		infix = characterPairs.matcher(infix).replaceAll(" * ");
		// Adds parentheses to enforce order of operations
		infix = "((((" + times.matcher(plus.matcher(closeParen.matcher(openParen.matcher(infix).replaceAll("((((")).replaceAll("))))")).replaceAll("))+((")).replaceAll(")*(") + "))))";
		// Splits infix into tokens
		return infixSplitter.splitAsStream(infix).collect(Collectors.toCollection(LinkedList::new));
	}
}
