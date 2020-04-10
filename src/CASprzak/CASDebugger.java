package CASprzak;

import java.util.Scanner;

public class CASDebugger {
	public static void main(String[] args) throws IllegalArgumentException {
		Scanner in = new Scanner(System.in);
		String userInput;

		System.out.println("Debug mode on? (Y/N)");
		userInput = in.nextLine().toUpperCase();
		if ("Y".equals(userInput))
			CASUI.debug = true;
		else if ("N".equals(userInput))
			CASUI.debug = false;
		else
			throw new IllegalArgumentException(userInput + " is not Y/N.");


			System.out.println("Would you like to exit (E), run CASUI (1), or directly use ParserXYZ (2)?");
		userInput = in.nextLine().toUpperCase();
		switch (userInput) {
			case "E":
				System.exit(0);
			case "1":
				CASUI.main(args);
				break;
			case "2":
				System.out.println("Input?");
				Function function = (new Parser('x', 'y', 'z')).parse(in.nextLine());
				System.out.println("toString:");
				System.out.println(function);
				System.out.println("Simplified once:");
				System.out.println(function = function.simplify());
				System.out.println("Simplified fully:");
				System.out.println(function = function.simplifyTimes(10));

				System.out.println("Evaluate? Enter comma-separated input to evaluate or '!' otherwise.");
				userInput = in.nextLine();
				if (userInput.charAt(0) !=  '!') {
					String[] unparsedValues = userInput.split(",");
					double[] values = new double[unparsedValues.length];
					for (int i = 0; i < values.length; i++)
						values[i] = ConstantEvaluator.getConstant(unparsedValues[i]);
					System.out.println(function.evaluate(values));
				}
				break;
			default:
				throw new IllegalArgumentException(userInput + " is not supported.");
		}
	}
}
