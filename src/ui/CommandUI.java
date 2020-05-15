package ui;

import config.Settings;
import parsing.KeywordInterface;
import tools.exceptions.CommandNotFoundException;

import java.util.Arrays;
import java.util.Scanner;

/**
 * TODO explain
 */
public class CommandUI {
	/**
	 * Runs {@link KeywordInterface#useKeywords} on user input
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
					output(KeywordInterface.useKeywords(input));
				} catch (RuntimeException e) {
					output(e);
					if (e instanceof CommandNotFoundException) {
						System.out.print("When parsing the input as a raw function, the following exception was thrown: ");
						output(KeywordInterface.prev);
					}
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
			else if (object instanceof Exception e)
				if (Settings.printStackTraces)
					e.printStackTrace();
				else
					System.out.println(e.toString());
			else
				System.out.println(object);
		} else {
			System.out.println("Done");
		}
	}
}
