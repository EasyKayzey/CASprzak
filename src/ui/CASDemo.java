package ui;

import parsing.KeywordInterface;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class CASDemo {
	protected enum DemoState {
		INTRO,
		EVAL,
		STO,
		SIMP,
		PD,
		EXIT,
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
			case STO -> sto();
			case SIMP -> simp();
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
		System.out.println("First, try typing in a function like \"x^2\" or \"cos(x) + sin(x)\".");
		if (!tryInput(s -> true, null))
			return;
		sleep(1);
		System.out.println("Congratulations, you have just inputted a function");
		sleep(1.25);
		System.out.println("Now, enter an underscore to call your previous output");
		if (!tryInput("_"::equals, "Please enter an underscore to demonstrate the previous-output feature of the UI."))
			return;
		sleep(1);
		System.out.println("You can see that it called the function that you last typed in.");
		sleep(2);
		currentState = DemoState.EVAL;
	}

	private static void eval() {
		System.out.println("Now, lets do something with our function like evaluating it.");
		sleep(2.25);
		System.out.println("Try typing in \"eval _ 2\" for example to evaluate your function at point 2.");
		if (!tryInput(s -> "eval".equals(s.substring(0, 4)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
			return;
		sleep(1);
		System.out.println("Now, try entering an underscore.");
		if (!tryInput("_"::equals, "Please enter an underscore"))
			return;
		sleep(1);
		System.out.println("Notice how underscore now saves the result from the evaluation.");
		sleep(2);
		currentState = DemoState.STO;
	}

	private static void sto() {
		System.out.println("If we want to use a function several times, we are going to have to store it.");
		sleep(3);
		System.out.println("Try typing in \"sto f 1-x^2\" for example to store the function \"1-x^2\" in \"f\".");
		if (!tryInput(s -> "sto".equals(s.substring(0, 3)), "Begin your input with 'sto' to demonstrate the storage feature of the UI."))
			return;
		sleep(1);
		System.out.println("Great, we can now use the function stored whenever we want");
		sleep(2);
		System.out.println("As a quick example, evaluate the function that you have stored by using the character that you stored in place of the underscore");
		sleep(2);
		if (!tryInput(s -> "eval".equals(s.substring(0, 4)), "Begin your input with 'eval'"))
			return;
		System.out.println("Good. Now here are some quick tips for using the storage feature:");
		sleep(2);
		System.out.println("If you store a function to character that is already used, it will be overwritten.");
		sleep(2);
		System.out.println("You can see all your function characters by typing in \"printfunctions\"");
		sleep(2);
		System.out.println("You can also remove a function by using the \"removefunction\" command or remove all functions by using\"clearfunctions\"");
		sleep(4);
		currentState = DemoState.SIMP;
	}

	private static void simp() {

	}

	private static void pd() {

	}

}

