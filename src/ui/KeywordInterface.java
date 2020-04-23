package ui;

import functions.Function;
import functions.special.Variable;
import parsing.Parser;

import java.util.Arrays;
import java.util.Scanner;
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
	 * Runs {@link #useKeywords} on user input
	 * @param args default main arguments
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\\n");
		boolean flag = true;
		while (flag) {
			String input = scan.next();
			if ("exit".equals(input.substring(0, 4)))
				flag = false;
			else {
				try {
					System.out.println(useKeywords(input));
					flag = true;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					flag = false;
				}
			}
		}
	}

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 * @return the Object requested
	 */
	public static Object useKeywords(String input) {
		String[] splitInput = spaces.split(input, 2);
		if (Arrays.asList(keywordSets[0]).contains(splitInput[0]))
			pd(splitInput[1]);
		else if (Arrays.asList(keywordSets[1]).contains(splitInput[0]))
			eval(splitInput[1]);
		else if (Arrays.asList(keywordSets[2]).contains(splitInput[0]))
			simp(splitInput[1]);
		else if (Arrays.asList(keywordSets[3]).contains(splitInput[0]))
			sub(splitInput[1]);
		else if (Arrays.asList(keywordSets[4]).contains(splitInput[0]))
			sol(splitInput[1]);
		else if (Arrays.asList(keywordSets[5]).contains(splitInput[0]))
			ext(splitInput[1]);
		else if (Arrays.asList(keywordSets[6]).contains(splitInput[0]))
			tay(splitInput[1]);
		else if (Arrays.asList(keywordSets[7]).contains(splitInput[0]))
			sto(splitInput[1]);
		else {
			throw new IllegalArgumentException(splitInput[0] + " is not supported by KeywordInterface");
		}
		return null;
	}

	public static Function pd(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).getSimplifiedDerivative(Variable.getVarID(splitInput[0].charAt(0)));
	}

	public static double eval(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).evaluate(); //TODO make this actually use the user input
	}

	public static Function simp(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).simplify();
	}

	public static Function sub(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return Parser.parse(splitInput[1]).substitute('\0', null); //TODO make this actually use user input
	}

	public static double[] sol(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return new double[0]; //TODO make this actually use user input
	}

	public static double[] ext(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return new double[0]; //TODO make this actually use user input
	}

	public static Function tay(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		return null; //TODO make this actually use user input
	}

	public static void sto(String input) {
		String[] splitInput = spacesOutsideQuotes.split(input);
		//TODO make this actually use user input
	}
}
