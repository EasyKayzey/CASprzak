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
		if (Arrays.stream(keywordSets[0]).anyMatch(input[0]::equals))
			pd(input[1]);
		else if (Arrays.stream(keywordSets[1]).anyMatch(input[0]::equals))
			eval(input[1]);
		else if (Arrays.stream(keywordSets[2]).anyMatch(input[0]::equals))
			simp(input[1]);
		else if (Arrays.stream(keywordSets[3]).anyMatch(input[0]::equals))
			sub(input[1]);
		else if (Arrays.stream(keywordSets[4]).anyMatch(input[0]::equals))
			sol(input[1]);
		else if (Arrays.stream(keywordSets[5]).anyMatch(input[0]::equals))
			ext(input[1]);
		else if (Arrays.stream(keywordSets[6]).anyMatch(input[0]::equals))
			tay(input[1]);
		else if (Arrays.stream(keywordSets[7]).anyMatch(input[0]::equals))
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
