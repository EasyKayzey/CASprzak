package ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	private static final Pattern COMPILE = Pattern.compile("\\s+");
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
		//noinspection StatementWithEmptyBody
		while (useKeywords(scan.next()));
	}

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 */
	public static boolean useKeywords(String input) {
		String[] splitInput = COMPILE.split(input, 2);
		if ("exit".equals(splitInput[0]))
			return false;
		else if (Arrays.asList(keywordSets[0]).contains(splitInput[0]))
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
			System.out.println(splitInput[0] + " is not supported by KeywordInterface");
			return false;
		}
		return true;
	}

	private static void pd(String input) {
	}

	private static void eval(String input) {
	}

	private static void simp(String input) {
	}

	private static void sub(String input) {
	}

	private static void sol(String input) {
	}

	private static void ext(String input) {
	}

	private static void tay(String input) {
	}

	private static void sto(String input) {
	}
}
