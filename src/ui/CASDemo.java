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
		VAR,
		SUB,
		PD,
		TAY,
		INT,
		INTN,
		SOL,
		EXT,
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
			case VAR -> var();
			case SUB -> sub();
			case PD -> pd();
			case TAY -> tay();
			case INT -> ints();
			case INTN -> intn();
			case SOL -> sol();
			case EXT -> ext();
		}
	}

	private static boolean tryInput(Predicate<? super String> test, String message) {
		try {
			System.out.print(">>> ");
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
		System.out.println("Try typing in \"eval _ x=2\" for example to evaluate your function at point 2.");
		if (!tryInput(s -> "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
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
		if (!tryInput(s -> "sto ".equals(s.substring(0, 4)), "Begin your input with 'sto' to demonstrate the storage feature of the UI."))
			return;
		sleep(1);
		System.out.println("Great, we can now use the function stored whenever we want.");
		sleep(2);
		System.out.println("As a quick example, evaluate the function that you have stored by using the character that you stored in place of the underscore.");
		if (!tryInput(s -> "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'"))
			return;
		sleep(1);
		System.out.println("Good. Now here are some quick tips for using the storage feature:");
		sleep(3);
		System.out.println("If you store a function to character that is already used, it will be overwritten.");
		sleep(3);
		System.out.println("You can see all your function characters by typing in \"printfunctions\""); // TODO always introduce the shortcuts, like pf
		sleep(3);
		System.out.println("You can also remove a function by using the \"removefunction\" command or remove all functions by using \"clearfunctions\"");
		sleep(5);
		currentState = DemoState.SIMP;
	}

	private static void simp() {
		System.out.println("Now, one of the most important features of our CAS is its ability to simplify expressions.");
		sleep(2);
		System.out.println("For example");
		sleep(1);
		System.out.println(">>> sto d x^2*(0*ln(x)+(2*1)/x)");
		sleep(1.5);
		System.out.println(KeywordInterface.useKeywords("sto d x^2*(0*ln(x)+(2*1)/x)"));
		sleep(2);
		System.out.println("This expression looks complicated, right?");
		sleep(2);
		System.out.println("Try typing \"simp d\" to return a simplified form of the expression");
		if (!tryInput(s -> "simp ".equals(s.substring(0, 5)), "Begin your input with 'simp' to demonstrate the simplification feature of the UI."))
			return;
		sleep(1);
		System.out.println("Wow!, quite an improvement");
		sleep(2);
		System.out.println("For those who are curious, that was the general formula of the derivative of f(x)^g(x) applied to x^2.");
		sleep(4);
		currentState = DemoState.VAR;
	}

	private static void var() {
		System.out.println("Until this point, all the expressions that we have been using have only contained one variable.");
		sleep(2);
		System.out.println("Type in \"vars\" to see all current variables");
		if (!tryInput("vars"::equals, "Please type in 'vars' to see the variables."))
			return;
		sleep(1);
		System.out.println("These are all the current variables.");
		sleep(2);
		System.out.println("Try adding a variable by using \"addvar t\" for example to add the variable \"t\".");
		if (!tryInput(s -> "addvar ".equals(s.substring(0, 7)), "Begin your input with 'addvar' to add variables."))
			return;
		sleep(1);
		System.out.println("Now we will construct a construct a function using more than one variable.");
		sleep(2);
		System.out.println("Store a multivariable function using the \"sto\" command"); // TODO make this more descriptive
		if (!tryInput(s -> "sto ".equals(s.substring(0, 4)), "Begin your input with 'sto' to demonstrate the storage feature of the UI."))
			return;
		sleep(1);
		System.out.println("Now, when we evaluate we are going to have to set all the variables equal to values, for example \"eval x-y x=2 y=3\".");
		sleep(2.5);
		System.out.println("Try evaluating your multivariable function using \"eval\"");
		if (!tryInput(s -> "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'"))
			return;
		sleep(1.5);
		System.out.println("Now, some more quick tips:");
		sleep(2);
		System.out.println("\"addvar\" can add several variables at a time.");
		sleep(2);
		System.out.println("and variables can be removed using \"removevar\" or all cleared using \"clearvars\".");
		sleep(4);
		currentState = DemoState.SUB;
	}

	private static void sub() {
		System.out.println("Now, with several variables, we can begin to substitute.");
		sleep(2.5);
		System.out.println("It is probably best to explain with an example:");
		sleep(2.5);
		System.out.println(">>> sub x^y y sin(y)");
		sleep(3);
		System.out.println(KeywordInterface.useKeywords("sub x^y y sin(x)"));
		sleep(2.25);
		System.out.println("As you can see it replaces all \"y\"s in the function with \"sin(x)\"");
		sleep(2);
		System.out.println("Now, lets try this for yourself:");
		sleep(2);
		System.out.println("Take your multivariable function from before and replace a variable with a function using \"sub\".");
		if (!tryInput(s -> "sub ".equals(s.substring(0, 4)), "Begin your input with 'sto' to demonstrate the substitution feature of the UI."))
			return;
		sleep(2);
		System.out.println("Congratulations, you have just substituted a variable for a function.");
		sleep(2);
		System.out.println("An important to remembers is that these changes are not saved in the function.");
		sleep(3);
		System.out.println("You would have to do \"sto g sub g x x^2\" for example.");
		sleep(4);
		currentState = DemoState.PD;
	}

	private static void pd() {
		System.out.println("Another feature of our CAS is the ability to find the partial derivative of any function.");
		sleep(3); // TODO d/dx will no longer return the derivative soon; use pd or partialderivative
		System.out.println(">>> d/dx x^2");
		System.out.println(KeywordInterface.useKeywords("d/dx x^2"));
		sleep(2);
		System.out.println(">>> d/dx e^(sin(x)+y)"); // TODO five of these is too much IMO
		System.out.println(KeywordInterface.useKeywords("d/dx e^(sin(x)+y)"));
		sleep(2);
		System.out.println(">>> d/dx x^(x^(x^x))");
		System.out.println(KeywordInterface.useKeywords("d/dx x^(x^(x^x))"));
		sleep(2);
		System.out.println(">>> d/dy logb_{x^y+z}(2sin(y))");
		System.out.println(KeywordInterface.useKeywords("d/dy logb_{x^y+z}(2sin(y))"));
		sleep(4);
		System.out.println("Now try it yourself using the \"d/d\" command.");
		if (!tryInput(s -> "d/d".equals(s.substring(0, 3)), "Begin your input with 'd/d' to demonstrate the derivative feature of the UI."))
			return;
		sleep(3);
		System.out.println("Great, you have taken the derivative of a function.");
		sleep(4);
		currentState = DemoState.TAY;
	}

	private static void tay() {
		System.out.println("One application of the derivative is in the form of a taylor series.");
		sleep(3);
		System.out.println("The format for creating a taylor series goes as follows:");
		sleep(3);
		System.out.println(">>> tay [function] [terms] [center]");
		sleep(2);
		System.out.println("Try creating a common taylor series using the \"tay\" command ");
		if (!tryInput(s -> "tay ".equals(s.substring(0, 4)), "Begin your input with 'tay' to demonstrate the taylor series feature of the CAS."))
			return;
		System.out.println("Good!, taylor series can often be used as good approximations to normal functions.");
		sleep(4);
		currentState = DemoState.INT;
	}

	private static void ints() {
		System.out.println("Integration is the other great pillar of calculus.");
		sleep(3);
		System.out.println("Unfortunately, our CAS can only symbolically solve integrals of the form ∫ c op(u) u' du");
		sleep(3);
		System.out.println("However, we do plan on further implementing symbolic integration.");
		sleep(3);
		System.out.println("An example of integration is as follows:");
		sleep(2);
		System.out.println(">>> int (cos(e^x))^2*sin(e^x)*e^x dx");
		System.out.println(KeywordInterface.useKeywords("int (cos(e^x))^2*sin(e^x)*e^x dx"));
		sleep(4);
		System.out.println("Try integration for yourself using the \"int\" command.");
		sleep(1);
		if (!tryInput(s -> "int ".equals(s.substring(0, 4)), "Begin your input with 'int' to demonstrate the integration feature of the CAS."))
			return;
		sleep(2);
		System.out.println("Congratulations, you have just integrated a function.");
		sleep(4);
		currentState = DemoState.INTN;
	}

	private static void intn() {

	}

	private static void sol() {

	}

	private static void ext() {

	}
}

