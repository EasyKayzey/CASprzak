package show.ezkz.casprzak.commandui;

import show.ezkz.casprzak.core.config.CoreSettingsParser;
import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.tools.exceptions.CommandNotFoundException;
import show.ezkz.casprzak.core.tools.exceptions.UserExitException;

import java.io.File;
import java.io.IOException;
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
		if (Settings.readProperties) try {
			CoreSettingsParser.parseGlobalConfig(new File("global.properties")); // TODO find real file
		} catch (IOException e) {
			System.out.println("Properties file not found. Using defaults...");
		}
		System.out.println("Welcome to CASprzak. Run 'help' for a command list, or 'demo' for a tutorial.");
		Scanner scanner = new Scanner(System.in);
		boolean flag = true;
		while (flag) {
			System.out.print(">>> ");
			String input = scanner.nextLine();
			if  (input.isBlank())
				continue;
			try {
				output(KeywordInterface.useKeywords(input));
			} catch (UserExitException ignored) {
				flag = false;
			} catch (RuntimeException e) {
				output(e);
				if (e instanceof CommandNotFoundException)
					System.out.println("To see details, enter 'err'.");
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
