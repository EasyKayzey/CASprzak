package ui;

import config.Settings;
import parsing.KeywordInterface;
import tools.ParsingTools;
import tools.exceptions.CommandNotFoundException;

import java.util.Arrays;
import java.util.Scanner;

/**
 * {@link CommandUI} is the main executable of {@code CASprzak}. It opens a command line interface where user input is fed into {@link KeywordInterface#useKeywords(String)} until {@code "exit"} or {@code "!"} is called.
 * The output from {@link KeywordInterface} is then printed to the command line.
 */
public class CommandUI {
	/**
	 * Runs {@link KeywordInterface#useKeywords} on user input
	 * @param args default main arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to CASprzak. Run 'help' for a command list, or 'demo' for a tutorial.");
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter(ParsingTools.newline);
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
						System.out.println("When parsing the input as a raw function, an exception was thrown. To see details, enter '_'.");
//						output(KeywordInterface.prev);
					}
				}
			}
		}
	}

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
