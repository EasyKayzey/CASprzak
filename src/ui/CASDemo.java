package ui;

import parsing.KeywordInterface;

import java.util.Scanner;
import java.util.function.Predicate;

public class CASDemo {
	protected enum DemoState {
		EXIT,
		INTRO,
		EVAL,
		PD,
	}

	public static DemoState currentState = DemoState.INTRO;
	protected static Scanner scanner;

	public static String runDemo() {
		scanner = new Scanner(System.in);
		scanner.useDelimiter("\n");
		while (currentState != DemoState.EXIT)
			runState();
		return "Exited demo.";
	}

	private static void runState() {
		switch (currentState) {
			case EXIT -> exit();
			case INTRO -> intro();
			case EVAL -> eval();
			case PD -> pd();
		}
	}

	private static boolean tryInput(Predicate<? super String> test, String message) {
		try {
			String input = scanner.next();
			if ('!' == input.charAt(0) || (input.length() >= 4 && "exit".equals(input.substring(0, 4)))) {
				currentState = DemoState.EXIT;
				return false;
			} else if (!test.test(input)) {
				System.out.println(message);
				return tryInput(test, message);
			} else {
				System.out.println(KeywordInterface.useKeywords(input));
				return true;
			}
		} catch (Exception e) {
			System.out.println("Your input threw exception '" + e.getMessage() + "', please try again.");
			return tryInput(test, message);
		}
	}

	private static void exit() {
	}

	private static void intro() {
		System.out.println("This is an intro. Actual help and intro would go here.");
		System.out.println("Demo user input");
		if (!tryInput(s -> true, null))
			return;
		System.out.println("Demo prev, enter underscore to use your previous output as the next input or smth");
		if (!tryInput("_"::equals, "Please enter an underscore to demonstrate the previous-output feature of the UI."))
			return;
		System.out.println("Do other things in intro to demo");
		currentState = DemoState.EVAL;
	}

	private static void eval() {
		System.out.println("eval things");
		if (!tryInput(s -> "eval".equals(s.substring(0, 4)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
			return;
		System.out.println("more eval things");
	}

	private static void pd() {
	}

}

