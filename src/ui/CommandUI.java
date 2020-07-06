package ui;

import config.Settings;
import parsing.KeywordInterface;
import tools.ParsingTools;
import tools.exceptions.CommandNotFoundException;
import tools.exceptions.UserExitException;

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
			if  (input.isBlank())
				continue;
			try {
				output(KeywordInterface.useKeywords(input));
			} catch (UserExitException ignored) {
				flag = false;
			} catch (RuntimeException e) {
				output(e);
				if (e instanceof CommandNotFoundException) {
					System.out.println("When parsing the input as a raw function, an exception was thrown. To see details, enter '_'.");
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
			System.out.println("Output is empty.");
		}
	}
}
