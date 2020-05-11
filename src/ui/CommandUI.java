package ui;

import parsing.KeywordInterface;

import java.util.Arrays;
import java.util.Scanner;

public class CommandUI extends KeywordInterface {
	/**
	 * Runs {@link #useKeywords} on user input
	 * @param args default main arguments
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter("\\n");
		boolean flag = true;
		while (flag) {
			System.out.print(">>> ");
			String input = scan.next();
			if  (input.length() == 0)
				continue;
			if ('!' == input.charAt(0) || (input.length() == 4 && "exit".equals(input.substring(0, 4))))
				flag = false;
			else {
				try {
					output(useKeywords(input));
				} catch (RuntimeException e) {
					System.out.println(e.toString());
				}
			}
		}
	}

	@SuppressWarnings("ChainOfInstanceofChecks")
	private static void output(Object object) {
		if (object != null) {
			if (object instanceof double[] array)
				System.out.println(Arrays.toString(array));
			else if (object instanceof Object[] array)
				System.out.println(Arrays.toString(array));
			else
				System.out.println(object);
		} else {
			System.out.println("Done");
		}
	}
}
