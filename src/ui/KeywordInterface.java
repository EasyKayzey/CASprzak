package ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterface {
	private static final Pattern spaces = Pattern.compile("\\s+");
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

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\\n");
		String[] input = spaces.split(scan.next(), 1);
		// Partials
		if (Arrays.asList(keywordSets[0]).contains(input[0]))
			pd(input[1]);
		else if (Arrays.asList(keywordSets[1]).contains(input[0]))
			eval(input[1]);
		else if (Arrays.asList(keywordSets[2]).contains(input[0]))
			simp(input[1]);
		else if (Arrays.asList(keywordSets[3]).contains(input[0]))
			sub(input[1]);
		else if (Arrays.asList(keywordSets[4]).contains(input[0]))
			sol(input[1]);
		else if (Arrays.asList(keywordSets[5]).contains(input[0]))
			ext(input[1]);
		else if (Arrays.asList(keywordSets[6]).contains(input[0]))
			tay(input[1]);
		else if (Arrays.asList(keywordSets[7]).contains(input[0]))
			sto(input[1]);
		else
			System.out.println(input[0] + " is not supported by KeywordInterface");
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
