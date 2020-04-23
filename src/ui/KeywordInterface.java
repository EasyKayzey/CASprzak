package ui;

import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
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
		try {
			//noinspection InfiniteLoopStatement
			while (true)
				useKeywords(scan.next());
		} catch (IllegalArgumentException ignored) {
//			ignored.printStackTrace();
		}
	}

	/**
	 * Takes input as a string with command, arguments...
	 * @param input contains the command and arguments
	 */
	public static void useKeywords(String input) {
		@SuppressWarnings("DynamicRegexReplaceableByCompiledPattern")
		String[] splitInput = input.split("\\s+", 2);
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
		else
			throw new IllegalArgumentException(splitInput[0] + " is not supported by KeywordInterface");
	}

	private static void pd(String s) {
	}

	private static void eval(String s) {
	}

	private static void simp(String s) {
	}

	private static void sub(String s) {
	}

	private static void sol(String s) {
	}

	private static void ext(String s) {
	}

	private static void tay(String s) {
	}

	private static void sto(String s) {
	}
}
