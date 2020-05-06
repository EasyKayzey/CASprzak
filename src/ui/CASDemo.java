package ui;

import parsing.KeywordInterface;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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

	@SuppressWarnings("SameReturnValue")
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

	private static void sleep(double seconds) {
		try {
			TimeUnit.MILLISECONDS.sleep((long) (seconds*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void exit() {
	}

	private static void intro() {
		System.out.println("Hello user. Welcome to the CASprzak!");
		sleep(1.5);
		System.out.println("This is a quick demo to help get you started.");
		sleep(2);
		System.out.println("First, try typing in a function like x^2 or cos(x).");
		if (!tryInput(s -> true, null))
			return;
		sleep(1);
		System.out.println("Congratulations, you have just inputted a function");
		sleep(1);
		System.out.println("Now, enter an underscore to call your previous output");
		if (!tryInput("_"::equals, "Please enter an underscore to demonstrate the previous-output feature of the UI."))
			return;
		sleep(1);
		System.out.println("You can see that it called the function that you last typed in.");
		sleep(1);
		currentState = DemoState.EVAL;
	}

	private static void eval() {
		System.out.println("Now, lets do something with our function like evaluating it.");
		System.out.println("Try tying in \"eval _ 2\" for example to evaluate your function at point 2");
		if (!tryInput(s -> "eval".equals(s.substring(0, 4)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
			return;
		System.out.println("more eval things");
	}

	private static void pd() {
	}

}

