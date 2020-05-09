package ui;

import parsing.KeywordInterface;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

@SuppressWarnings("SpellCheckingInspection")
public class CASDemo {
	protected enum DemoState {
		INTRO,
		EVAL,
		STO,
		SIMP,
		VAR,
		SUB,
		PD,
		TAY,
		INT,
		INTN,
		SOL,
		EXT,
		SET,
		END,
		EXIT,
	}

	public static DemoState currentState = DemoState.INTRO;
	protected static Scanner scanner;

	@SuppressWarnings("SameReturnValue")
	public static String runDemo() {
		scanner = new Scanner(System.in);
		scanner.useDelimiter("\n.");
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
			case VAR -> var();
			case SUB -> sub();
			case PD -> pd();
			case TAY -> tay();
			case INT -> ints();
			case INTN -> intn();
			case SOL -> sol();
			case EXT -> ext();
			case SET -> set();
			case END -> end();
		}
	}

	private static boolean tryInput(Predicate<? super String> test, String message) {
		return tryInput(test, message, null);
	}
	
	private static boolean tryInput(Predicate<? super String> test, String message, String input) {
		try {
			System.out.print(">>> ");
			if (input == null)
				input = scanner.next();
			if ('!' == input.charAt(0) || (input.length() >= 4 && "exit".equals(input.substring(0, 4)))) {
				currentState = DemoState.EXIT;
				return false;
			} else if (input.length() >= 4 && "next".equals(input.substring(0, 4))) {
				return true;
			} else if (!test.test(input)) {
				System.out.println(message);
				return tryInput(test, message);
			} else {
				System.out.println(KeywordInterface.useKeywords(input));
				return true;
			}
		} catch (Exception e) {
			System.out.println("Your input threw exception '" + e.toString() + "', please try again.");
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
		sleep(1.5);
		System.out.println("You can exit the demo anytime by typing 'exit' or '!'.");
		sleep(1.5);
		System.out.println("First, try typing in a function like 'x^2' or 'cos(x) + sin(x)'.");
		if (!tryInput(s -> true, null))
			return;
		sleep(1.5);
		System.out.println("Congratulations, you've just inputted your first function.");
		sleep(1.5);
		System.out.println("Now, enter an underscore to reference your previous output.");
		if (!tryInput("_"::equals, "Please enter an underscore to demonstrate the previous-output feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("You can see that this references the function that you last typed in.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.EVAL;
	}

	private static void eval() {
		System.out.println("Now, let's evaluate our function.");
		sleep(1.5);
		System.out.println("Try typing in 'eval _ x=2' for example to evaluate your function at point 2.");
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Now, try entering an underscore.");
		if (!tryInput("_"::equals, "Please enter an underscore."))
			return;
		sleep(1.5);
		System.out.println("Notice how underscore now saves the result from the evaluation.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();

		currentState = DemoState.STO;
	}

	private static void sto() {
		System.out.println("If we want to use a function several times, we are going to have to store it using the 'store' or 'sto' command.");
		sleep(1.5);
		System.out.println("For example, try typing in 'sto f 1-x^2' to store the function '1-x^2' in 'f'.");
		if (!tryInput(s -> s.length() > 2 && "sto".equals(s.substring(0, 3)), "Begin your input with 'sto' to demonstrate the storage feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Great, we can now use this function whenever we want.");
		sleep(1.5);
		System.out.println("Evaluate the function that you have stored by using the character that you stored in place of the underscore, e.g. 'eval f x=2'.");
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'."))
			return;
		sleep(1.5);
		System.out.println("Good. Here are some tips for using the storage feature:");
		sleep(1.5);
		System.out.println("If you store a function to character that is already used, the old function will be overwritten.");
		sleep(1.5);
		System.out.println("You can see all your function characters by typing in 'printfunctions' or 'pf'.");
		sleep(1.5);
		System.out.println("You can also remove a function by using the 'removefunction' or 'rmf' command, or remove all functions by using 'clearfunctions'.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.SIMP;
	}

	private static void simp() {
		System.out.println("Now, one of the most important features of our CAS is its ability to simplify expressions.");
		sleep(1.5);
		System.out.println("For example:");
		sleep(1.5);
		System.out.println(">>> sto d x^2*(0*ln(x)+(2*1)/x)");
		sleep(1.5);
		System.out.println(KeywordInterface.useKeywords("sto d x^2*(0*ln(x)+(2*1)/x)"));
		sleep(1.5);
		System.out.println("This expression looks complicated, right?");
		sleep(1.5);
		System.out.println("Try typing 'simp d' to return a simplified form of the expression.");
		if (!tryInput(s -> s.length() > 4 && "simp ".equals(s.substring(0, 5)), "Begin your input with 'simp' to demonstrate the simplification feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Wow, quite the improvement.");
		sleep(1.5);
		System.out.println("For those who are curious, that was the general formula of the derivative of 'f(x)^g(x)' applied to 'x^2'.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.VAR;
	}

	private static void var() {
		System.out.println("Until this point, all the expressions that we've been using have only contained one variable.");
		sleep(1.5);
		System.out.println("Type in 'vars' to see all current variables.");
		if (!tryInput("vars"::equals, "Please type in 'vars' to see the variables."))
			return;
		sleep(1.5);
		System.out.println("These are all the current variables.");
		sleep(1.5);
		System.out.println("Try adding a variable by using 'addvar t' to add the variable 't'."); // TODO remove this when removing variables
		if (!tryInput(s -> "addvar ".equals(s.substring(0, 7)), "Begin your input with 'addvar' to add variables."))
			return;
		sleep(1.5);
		System.out.println("Now we will construct a function using more than one variable.");
		sleep(1.5);
		System.out.println("Store a function with several different variables using the 'sto' command, e.g. 'sto g xy^2+q'.");
		if (!tryInput(s -> "sto ".equals(s.substring(0, 4)), "Begin your input with 'sto' to demonstrate the storage feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Now, when evaluating, you will need to specify each value, like 'eval x-y x=2 y=3'.");
		sleep(1.5);
		System.out.println("Try evaluating your multivariable function using 'eval', e.g. 'eval f x=2 y=4 q=e.");
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'"))
			return;
		sleep(1.5);
		System.out.println("Now, some more quick tips:");
		sleep(1.5);
		System.out.println("'addvar' or 'addv' can add several variables at a time, e.g. 'vars a b c d e f g'.");
		sleep(1.5);
		System.out.println("Variables can be removed using 'removevar' or 'rmv', and cleared using 'clearvars'.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.SUB;
	}

	private static void sub() {
		System.out.println("Now, with several variables, we can begin to substitute.");
		sleep(1.5);
		System.out.println("It is probably best to explain with an example:");
		sleep(1.5);
		System.out.println(">>> sub x^y y sin(x)");
		sleep(1.5);
		System.out.println(KeywordInterface.useKeywords("sub x^y y sin(x)"));
		sleep(1.5);
		System.out.println("As you can see, this replaces all 'y's in the function with 'sin(x)'.");
		sleep(1.5);
		System.out.println("To try it yourself, take your multivariable function from before and replace a variable with a function using 'sub'.");
		if (!tryInput(s -> s.length() > 3  && "sub ".equals(s.substring(0, 4)), "Begin your input with 'sto' to demonstrate the substitution feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Congratulations, you have just substituted a variable for a function.");
		sleep(1.5);
		System.out.println("An important thing to remembers is that these changes are not saved in the function.");
		sleep(1.5);
		System.out.println("For example, to substitute every 'x' in 'g' with 'x^2', you would need to run 'sto g sub g x x^2'.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.PD;
	}

	private static void pd() {
		System.out.println("Another feature of this CAS is its ability to compute the partial derivative of any function.");
		sleep(1.5);
		System.out.println("Some examples:");
		sleep(1.5);
		System.out.println(">>> pd x x^2");
		System.out.println(KeywordInterface.useKeywords("pd x x^2"));
		sleep(1.5);
		System.out.println(">>> pd x e^(sin(x)+y)");
		System.out.println(KeywordInterface.useKeywords("pd x e^(sin(x)+y)"));
		sleep(1.5);
		System.out.println(">>> pd x x^(x^(x^x))");
		System.out.println(KeywordInterface.useKeywords("pd x x^(x^(x^x))"));
		sleep(1.5);
		System.out.println(">>> pd y logb_{x^y+z}(2sin(y))");
		System.out.println(KeywordInterface.useKeywords("pd y logb_{x^y+z}(2sin(y))"));
		sleep(1.5);
		System.out.println("Now try it yourself using the 'pd' command, in the syntax 'pd [variable] [function]'.");
		if (!tryInput(s -> s.length() > 1 && "pd".equals(s.substring(0, 2)), "Begin your input with 'pd' to demonstrate the derivative feature of the UI."))
			return;
		sleep(1.5);
		System.out.println("Great, you have taken the derivative of a function.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.TAY;
	}

	private static void tay() {
		System.out.println("One application of the derivative is in the form of a taylor series.");
		sleep(1.5);
		System.out.println("The syntax for creating a taylor series goes as follows:");
		sleep(1.5);
		System.out.println(">>> tay [function] [terms] [center].");
		sleep(1.5);
		System.out.println("Try creating a taylor series using the 'tay' command.");
		if (!tryInput(s -> "tay ".equals(s.substring(0, 4)), "Begin your input with 'tay' to demonstrate the taylor series feature of the CAS."))
			return;
		System.out.println("Good. Taylor series can often be used as good approximations to difficult functions.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.INT;
	}

	private static void ints() {
		System.out.println("Integration is the other great pillar of calculus.");
		sleep(1.5);
		System.out.println("Currently, this CAS can only symbolically solve integrals of the form ∫ c op(u) u' du.");
		sleep(1.5);
		System.out.println("More general symbolic integration will be added in future releases.");
		sleep(1.5);
		System.out.println("An example where integration works is as follows:.");
		sleep(1.5);
		System.out.println(">>> int (cos(e^x))^2*sin(e^x)*e^x dx.");
		System.out.println(KeywordInterface.useKeywords("int (cos(e^x))^2*sin(e^x)*e^x dx"));
		sleep(1.5);
		System.out.println("Try integration for yourself using the 'int' command.");
		sleep(1.5);
		if (!tryInput(s -> s.length() > 3 && "int ".equals(s.substring(0, 4)), "Begin your input with 'int' to demonstrate the integration feature of the CAS."))
			return;
		sleep(1.5);
		System.out.println("Congratulations, you've just integrated your first function.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.INTN;
	}

	private static void intn() {
		System.out.println("Despite not being able to symbolically integrate all functions, it is still almost always possible to find a numeric solution between two bounds.");
		sleep(1.5);
		System.out.println("This CAS uses Simpson's Rule to be able to perform very accurate numeric integration.");
		sleep(1.5);
		System.out.println("The syntax for numeric integration is 'intn [function] [startvalue] [endvalue]'.");
		sleep(1.5);
		System.out.println("Try it out yourself using any function.");
		sleep(1.5);
		if (!tryInput(s -> "intn ".equals(s.substring(0, 5)), "Begin your input with 'intn' to demonstrate the numeric integration feature of the CAS."))
			return;
		sleep(1.5);
		System.out.println("Good job!");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.SOL;
	}

	private static void sol() {
		System.out.println("In addition to numerical integration, this CAS also implements numerical root finding.");
		sleep(1.5);
		System.out.println("The system we use to find zeroes requires a specified start and end to look for roots in.");
		sleep(1.5);
		System.out.println("The syntax is 'sol [function] [startrange] [endrange]'.");
		sleep(1.5);
		System.out.println("Try it out yourself:");
		sleep(1.5);
		if (!tryInput(s -> s.length() > 3 && "sol ".equals(s.substring(0, 4)), "Begin your input with 'sol' to demonstrate the solving feature of the CAS."))
			return;
		sleep(1.5);
		System.out.println("As with all numeric methods, there are some quirks.");
		sleep(1.5);
		System.out.println("Very often, simple solutions such as 1 will be found as a decimal like 1.000000000023.");
		sleep(1.5);
		System.out.println("There are some settings like exit conditions that change how the solver works which may help fix minor issues.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.EXT;
	}

	private static void ext() {
		System.out.println("With the ability to solve for zeros, we can now find extrema of functions.");
		sleep(1.5);
		System.out.println("For example:");
		sleep(1.5);
		System.out.println(">>> ext max 1-x^2 -2 2.");
		sleep(1.5);
		System.out.println(KeywordInterface.useKeywords("ext max 1-x^2 -2 2"));
		sleep(1.5);
		System.out.println("This command found the maximum of '1-x^2' on the range (-2, 2).");
		sleep(1.5);
		System.out.println("There are other commands in addition to just max.");
		sleep(1.5);
		System.out.println("The 5 options after 'ext' are 'min, max, anymin, anymax, inflection'.");
		sleep(1.5);
		System.out.println("min and max return the maximum or minimum of the function in the given range.");
		sleep(1.5);
		System.out.println("anymin, anymax, and inflection return an array of all local minima, maxima, or inflection points.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.SET;
	}

	private static void set() {
		System.out.println("Finally, we will show you how to view or modify settings.");
		sleep(1.5);
		System.out.println("Type in 'settings' to see all current settings.");
		if (!tryInput("settings"::equals, "Type in 'settings' to see the settings."))
			return;
		sleep(1.5);
		System.out.println("These are all the current settings.");
		sleep(1.5);
		System.out.println("If you want to change a setting, use 'setsetting [setting] [value]'.");
		sleep(1.5);
		System.out.println("As you saw, there are a lot of settings. If you want to learn what a setting does, we recommend reading our documentation.");
		sleep(1.5);
		System.out.println("Default settings are read from 'config/cas.properties' on launch. This file also contains documentation regarding the effects of various settings.");
		sleep(1.5);
		System.out.println("Continue testing this feature, or type 'next' to continue.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.END;
	}

	private static void end() {
		System.out.println("Thank you for completing the demo.");
		sleep(1.5);
		System.out.println("For more details such as more features and shortcuts in the UI, check out our documentation or give the code a read.");
		sleep(1.5);
		System.out.println("We highly encourage just playing around to see what you can do.");
		sleep(1.5);
		System.out.println("Continue testing in the demo, or type 'next' to return to the main command-line interface.");
		String input = scanner.next();
		while (!"next".equals(input))
			if (!tryInput(s -> true, null, input))
				return;
			else
				input = scanner.next();
		currentState = DemoState.EXIT; // TODO add a section about LaTeX
	}
}

