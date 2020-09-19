package show.ezkz.casprzak.parsing;

import show.ezkz.casprzak.core.config.Settings;

import java.util.List;
import java.util.regex.Pattern;

import static show.ezkz.casprzak.core.config.Settings.maxEscapeLength;

/**
 * {@link InfixTokenizer} modifies and tokenizes infix into a format supported by {@link FunctionParser}.
 */
public class InfixTokenizer {
	private static final String splitExcpt = Settings.doCombinatorics ? "ECP" : "E"; // splitterExceptions
	private static final Pattern absoluteValueEnd = Pattern.compile(
			"\\|(?=([^|]*\\|[^|]*\\|)*" +							// Ensures an even number of following absolute value signs
			"[^|]*$)"												// Allows for any number of following non-bar characters and asserts EOL position
	);
	private static final Pattern absoluteValueStart = Pattern.compile(
			"(?<=\\w)\\|"											// Only matches if preceded by a word character so '*abs(' can be substituted
	);
	private static final Pattern adjacentMultiplier = Pattern.compile(
			"(?<!\\\\[\\w.']{0," + maxEscapeLength + "})" +			// Ensures that the next two lines are not LaTeX-escaped (up to Settings.maxEscapeLength characters)
			"((?<=\\d)(?=[a-zA-Z])(?![" + splitExcpt + "])" +		// Matches if preceded by a digit and followed by a non-ECP letter
			"|(?<=[a-zA-Z])(?=\\.)" +								// Matches if preceded by a letter and followed by a period
			"|(?<=[a-zA-Z])(?<![" + splitExcpt + "])(?=[\\d]))" +	// Matches if preceded by a non-ECP letter and followed by a digit
			"|(?<=\\))(?=[\\w(])" + 								// Matches if preceded by ) and followed by a word character or (
			"|(?<=\\))(?=\\.)" +									// Matches if preceded by a letter or ) and followed by a period
			"|(?<=[\\d)])(?=\\()(?<!logb\\s?_\\d)"						// Matches if preceded by [a digit not preceded by logb_] or ) and followed by (
	);
	private static final Pattern subtractionFinder = Pattern.compile(
			"(?<!^)(?<!E)" +										// Ensures not preceded by newline or E
			"(?<![\\^\\-%+*,/\\s({])\\s*-"							// Ensures not preceded by an infix operation, comma, or (, then matches - signs and all spaces preceding
	);
	private static final Pattern openParen = Pattern.compile("\\(");
	private static final Pattern closeParen = Pattern.compile("\\)");
	private static final Pattern plus = Pattern.compile("\\+");
	private static final Pattern times = Pattern.compile("\\*");
	private static final Pattern modulo = Pattern.compile("%");
	private static final Pattern infixSplitter = Pattern.compile(
			"\\s+|(?<!\\s)(" + 										// Splits on spaces, and ensured spaces are not including in following splits
			"(?<=!)|(?=!)" +										// Splits if preceded or followed by !
			"|(?<!\\s)(?=\\\\)" +									// Splits if followed by a LaTeX escape
			"|(((?<=\\W)(?=[\\w-])((?<!-)|(?!\\d))" +				// Splits if preceded by non-word and followed by word and not [preceded by - and followed by a digit]
			"|(?<=\\w)(?=\\W)((?<!E)|(?!-)))" +						// Splits if preceded by a word and followed by a non-word, unless [the word was E and the non-word was -]
			"|(?<=[^\\x00-\\x7F])|(?=[^\\x00-\\x7F])" +				// Splits if preceded or followed by a non-ASCII character
			(Settings.doCombinatorics ? "|(?<=[CP])" : "") +		// Splits if preceded by a C or P and Settings.doCombinatorics is on
			(Settings.doCombinatorics ?  "|(?=[CP])" : "") +		// Splits if followed by a C or P and Settings.doCombinatorics is on
			"|(?<=[()])|(?=[()]))" +								// Splits if preceded or followed by a parenthesis
			"(?<![ .\\\\])(?![ .])" +								// The lines after "followed by LaTeX escape" ONLY WORK if not preceded or followed by a period or space, and not preceded by a LaTeX escape
			"|(?<=\\D)(?=\\.)" +									// Splits if preceded by a non-digit and followed by a period
			"|(?<=\\()(?=\\.))"										// Splits if preceded by an open parenthesis and followed by a period
	);
	private static final Pattern characterPairMultiplier = Pattern.compile(
			"(?<!\\\\[\\w.']{0," + maxEscapeLength + "})" +			// Ensures that the character is not LaTeX-escaped (up to Settings.maxEscapeLength characters)
			"(?<![CEP])(?![CEP])" +									// Ensures the spaces before and after C, E, and P are not matched
			"(?<!logb\\s?_\\w)" +										// Ensures not preceded by \logb_{character}
			"((?<!\\d)|(?!\\d))" +									// Ensures that spaces both preceded and followed by a digit are not matched
			"(?<=[a-zA-Z)\\d[^\\x00-\\x7F]])" +						// Preceded by a digit, alphabetic char, or non-ascii character
			"\\s*" + 												// Allows for spaces
			"(?=[a-zA-Z\\\\(\\d[^\\x00-\\x7F]])" 					// Followed by a digit, alphabetic char, open-parenthesis, or non-ascii character
	);
	private static final Pattern differential = Pattern.compile(
			"\\\\d\\s?(?=[a-zA-Z[^\\x00-\\x7F]](?![a-zA-Z[^\\x00-\\x7F]])|\\\\)"
	);
	private static final Pattern partialDerivative = Pattern.compile("d/d");
	private static final Pattern endPD = Pattern.compile(
			"(?<=\\\\pd\\{([a-zA-Z[^\\x00-\\x7F]]))"
	);
	private static final Pattern division = Pattern.compile("(?<!/)/(?!/)");
	private static final Pattern logbUnderscores = Pattern.compile("logb\\s?_");

	private InfixTokenizer(){}

	/**
	 * Tokenizes an input infix string and modifies it to a format supported by {@link FunctionParser}
	 * @param infix input string in infix
	 * @return array of modified infix tokens
	 */
	public static List<String> tokenizeInfix(String infix) {
		// Make d/dx into \pd x
		infix = endPD.matcher(partialDerivative.matcher(infix).replaceAll("\\\\pd{")).replaceAll("}");
		// Make absolute values into unitary functions
		infix = absoluteValueStart.matcher(absoluteValueEnd.matcher(infix).replaceAll(")")).replaceAll("*\\abs(").replace("|", " \\abs(");
		// Replaces brackets with parentheses
		infix = infix.replace("[", "(").replace("]", ")");
		// Insert multiplication in expressions like 2x and 7(x*y+1)sin(3y)
		infix = adjacentMultiplier.matcher(infix).replaceAll(" * ");
		// Turns expressions like x-y into x+-y, and turns expressions like x*y into x*/y (the '/' operator represents reciprocals)
		infix = subtractionFinder.matcher(division.matcher(infix).replaceAll("*/")).replaceAll("+-");
		// Turns differentials like `dx` and `d x` into `\d x`
		infix = differential.matcher(infix).replaceAll("\\\\difn ");
		// Turns expressions like xyz into x*y*z
		infix = characterPairMultiplier.matcher(infix).replaceAll(" * ");
		// Replace curly braces parentheses
		infix = infix.replace("{", "(").replace("}", ")");
		// Replaces logb underscores with spaces
		infix = logbUnderscores.matcher(infix).replaceAll("logb ");
		// Replaces commas with parenthesis sets
		infix = infix.replace(",", ") (");
		// Adds parentheses to enforce order of operations
		infix = "((((" + times.matcher(plus.matcher(closeParen.matcher(openParen.matcher(infix).replaceAll("((((")).replaceAll("))))")).replaceAll("))+((")).replaceAll(")*(") + "))))";
		// Adds parentheses to lower modulo precedence below multiplication
		infix = modulo.matcher(infix).replaceAll("))%((");
		// Splits infix into tokens
		return List.of(infixSplitter.split(infix));
	}
}
