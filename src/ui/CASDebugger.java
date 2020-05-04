package ui;

import functions.GeneralFunction;
import parsing.Parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CASDebugger {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String userInput;

//		System.out.println("Debug mode on? (Y/N)");
//		userInput = in.nextLine().toUpperCase();
//		if ("Y".equals(userInput))
//			Settings.debug = true;
//		else if ("N".equals(userInput))
//			Settings.debug = false;
//		else
//			throw new IllegalArgumentException(userInput + " is not Y/N.");

		System.out.println("Would you like to exit (E), run CASDemo (1), or directly use ParserXYZ (2)?");
		userInput = in.nextLine().toUpperCase();
		switch (userInput) {
			case "E" -> System.exit(0);
			case "1" -> CASDemo.main(args);
			case "2" -> {
				System.out.println("Input?");
				GeneralFunction function = Parser.parse(in.nextLine());
				System.out.println("toString:");
				System.out.println(function);
				System.out.println("Simplified once:");
				System.out.println(function.simplify());
				System.out.println("Evaluate? Enter comma-separated input to evaluate or '!' otherwise.");
				userInput = in.nextLine();
				if (userInput.charAt(0) != '!') {
					String[] unparsedValues = userInput.split(",");
					char[] chars = {'x', 'y', 'z'};
					Map<Character, Double> values = new HashMap<>();
					for (int i = 0; i < unparsedValues.length; i++)
						values.put(chars[i], Parser.getConstant(unparsedValues[i]));
					System.out.println(function.evaluate(values));
				}
			}
			default -> throw new IllegalArgumentException(userInput + " is not supported.");
		}
	}
}
