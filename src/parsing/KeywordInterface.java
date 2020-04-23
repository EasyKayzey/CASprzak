package parsing;

import functions.Function;
import functions.special.Variable;

import java.util.Arrays;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	private static final Pattern spaces = Pattern.compile("\\s+");
	public static final Pattern spacesOutsideQuotes = Pattern.compile("\"\\s\"|\"\\s|\\s\"|\"$|\\s+(?=[^\"]*(\"[^\"]*\"[^\"]*)*$)");
	/**
	 * A list of sets of keywords corresponding to operations
	 */
	public static String[][] keywordSets = {
			{"pd", "pdiff", "partial", "pdifferentiate"},
			{"eval", "evaluate"},
			{"simp", "simplify"},
			{"sub", "substitute"},
			{"sol", "solve"},
			{"ext", "extrema"},
			{"tay", "taylor"},
			{"sto", "store"},
	};

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		String[] splitInput = spaces.split(input, 2); //TODO MAKE THIS USE ENHANCED SWITCH
		if (Arrays.asList(keywordSets[0]).contains(splitInput[0]))
			return pd(splitInput[1]);
		else if (Arrays.asList(keywordSets[1]).contains(splitInput[0]))
			return eval(splitInput[1]);
		else if (Arrays.asList(keywordSets[2]).contains(splitInput[0]))
			return simp(splitInput[1]);
		else if (Arrays.asList(keywordSets[3]).contains(splitInput[0]))
			return sub(splitInput[1]);
		else if (Arrays.asList(keywordSets[4]).contains(splitInput[0]))
			return sol(splitInput[1]);
		else if (Arrays.asList(keywordSets[5]).contains(splitInput[0]))
			return ext(splitInput[1]);
		else if (Arrays.asList(keywordSets[6]).contains(splitInput[0]))
			return tay(splitInput[1]);
		else if (Arrays.asList(keywordSets[7]).contains(splitInput[0]))
			return sto(splitInput[1]);
		else {
			throw new IllegalArgumentException(splitInput[0] + " is not supported by KeywordInterface");
		}
	}

	/**
	 * pd [variable] [function]
	 */
	public static Function pd(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).getSimplifiedDerivative(Variable.getVarID(splitInput[0].charAt(0)));
	}

	/**
	 * eval [function] [values]
	 */
	public static double eval(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input, 1);
		double[] values =
		return Parser.parse(splitInput[0]).evaluate(); //TODO make this actually use the user input
	}

	/**
	 * simp [function]
	 */
	public static Function simp(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[0]).simplify();
	}

	/**
	 * sub [function] [variable] [replacementfunction]
	 */
	public static Function sub(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).substitute('\0', null); //TODO make this actually use user input
	}

	/**
	 * sol [function] [startrange] [endrange]
	 */
	public static double[] sol(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return new double[0]; //TODO make this actually use user input
	}

	/**
	 * ext ["min(ima)"/"max(ima)"/"anymin(ima)"/"anymax(ima)"/"inflect(ion)"] [function] [startrange] [endrange]
	 */
	public static double[] ext(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return new double[0]; //TODO make this actually use user input
	}

	/**
	 * tay [variable] [function]
	 */
	public static Function tay(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return null; //TODO make this actually use user input
	}

	/**
	 * sto [locationstring] [function]
	 */
	public static Function sto(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		//TODO make this actually use user input
		return null;
	}
}
