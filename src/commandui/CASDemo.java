package commandui;

import core.config.Settings;
import core.tools.VariableTools;
import core.tools.exceptions.UserExitException;

import java.util.Scanner;
import java.util.function.Predicate;

import static core.tools.MiscTools.printWithSleep;
import static core.tools.MiscTools.sleep;

/**
 * {@link CASDemo} provides a demo and tutorial of the CAS functionality for new users. The demo is started using the {@code demo} command in {@link KeywordInterface} or {@link CommandUI}.
 */
public class CASDemo {
	private enum DemoState {
		INTRO,
		EVAL,
		DEF,
		SIMP,
		VAR,
		SUB,
		SA,
		QUOTES,
		PD,
		TAY,
		INT,
		INTN,
		SOL,
		EXT,
		SET,
		LATEX,
		END,
		EXIT,
		TOC,
	}

	private static DemoState currentState = DemoState.INTRO;
	private static Scanner scanner;

	/**
	 * Runs the demo
	 * @return "Exiting demo..."
	 */
	@SuppressWarnings("SameReturnValue")
	public static String runDemo() {
		scanner = new Scanner(System.in);
		while (currentState != DemoState.EXIT)
			runState();
		return "Exiting demo...";
	}

	/**
	 * Resets the demo
	 */
	public static void reset() {
		currentState = DemoState.INTRO;
	}

	private static void runState() {
		switch (currentState) {
			case INTRO -> intro();
			case EVAL -> eval();
			case DEF -> def();
			case SIMP -> simp();
			case VAR -> var();
			case SUB -> sub();
			case SA -> sa();
			case QUOTES -> quotes();
			case PD -> pd();
			case TAY -> tay();
			case INT -> ints();
			case INTN -> intn();
			case SOL -> sol();
			case EXT -> ext();
			case SET -> set();
			case LATEX -> latex();
			case END -> end();
			case EXIT -> exit();
			case TOC -> tableOfContents();
		}
	}

	private static boolean tryInput(Predicate<? super String> test, String message) {
		return tryInput(test, message, null);
	}
	
	private static boolean tryInput(Predicate<? super String> test, String message, String input) {
		try {
			if (input == null) {
				System.out.print(">>> ");
				input = scanner.nextLine();
			}
			
			if (input.length() >= 4 && "next".equals(input.substring(0, 4))) {
				return true;
			} else if ("toc".equals(input.toLowerCase())) {
				currentState = DemoState.TOC;
				return false;
			} else if (!test.test(input)) {
				System.out.println(message);
				return tryInput(test, message);
			} else {
				System.out.println(KeywordInterface.useKeywords(input));
				return true;
			}
		} catch (UserExitException ignored) {
			currentState = DemoState.EXIT;
			return false;
		} catch (Exception e) {
			System.out.println("Your input threw exception '" + e.toString() + "', please try again.");
			if (Settings.printStackTraces)
				e.printStackTrace();
			return tryInput(test, message); // NOTE: this code means that if you have an error in runTillNext then try to exit with "next", you'll need to type it twice.
		}
	}

	private static void runTillNext() {
		System.out.print(">>> ");
		String input = scanner.nextLine();
		while (!"next".equals(input)) {
			if (!tryInput(s -> true, null, input))
				return;
			else {
				System.out.print(">>> ");
				input = scanner.nextLine();
			}
		}
	}

	private static void tableOfContents() {
		printWithSleep("""
				Table of Contents:
				INTRO -> introduction to CASprzak
				EVAL -> evaluation
				DEF -> function definition
				SIMP -> simplification
				VAR -> multivariable functions
				SUB -> substitution
				SA -> substitute-all
				QUOTES -> quotes tutorial
				PD -> partial derivatives
				TAY -> taylor series
				INT -> symbolic integration
				INTN -> numerical integration
				SOL -> numerical solving
				EXT -> extrema
				SET -> settings
				LATEX -> latex
				END -> closing
				EXIT -> exit
				Enter one of the above capital shortcuts above to go to that section of the demo.
				""", .25);
		while (true) try {
			System.out.print(">>> ");
			currentState = DemoState.valueOf(scanner.nextLine().toUpperCase());
			return;
		} catch (IllegalArgumentException e) {
			System.out.println("Your input was not one of the constants mentioned above. To exit, enter EXIT.");
		}
	}

	private static void exit() {
		currentState = DemoState.EXIT;
	}

	private static void intro() {
		printWithSleep("""
				Hello user. Welcome to the CASprzak!
				This is a quick demo to help get you started.
				You can exit the demo anytime by typing 'exit' or '!'.
				If you would like to only see a specific section of the demo, enter 'toc'. You can do this at any point. Otherwise, enter 'next'.
				""");
		if (!tryInput(s -> true, null))
			return;
		sleep();
		printWithSleep("""
				First, to demonstrate parsing, try typing in a function like 'x^2' or 'cos(x) + sin(x)'.
				""");
		if (!tryInput(s -> true, null))
			return;
		sleep();
		printWithSleep("""
				Congratulations, you've just parsed your first function.
				Now, enter an underscore to reference your previous output.
				""");
		if (!tryInput("_"::equals, "Please enter an underscore to demonstrate the previous-output feature of the UI."))
			return;
		sleep();
		printWithSleep("You can see that this references the function that you last typed in.", true);
		currentState = DemoState.EVAL;
	}

	private static void eval() {
		printWithSleep("""
				Now, let's evaluate our function.
				Try typing in 'eval _ x=2' for example to evaluate your function at point x=2.
				""");
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval' to demonstrate the evaluation feature of the UI."))
			return;
		sleep();
		printWithSleep("Now, try entering an underscore.");
		if (!tryInput("_"::equals, "Please enter an underscore."))
			return;
		sleep();
		printWithSleep("""
				Notice how underscore now saves the result from the evaluation.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.DEF;
		runTillNext();
	}

	private static void def() {
		printWithSleep("""
				If we want to use a function several times, we are going to have to define it using the 'def' command.
				For example, try typing in 'def f 1-x^2' to store the function '1-x^2' in 'f'.
				""");
		if (!tryInput(s -> s.length() > 2 && "def".equals(s.substring(0, 3)), "Begin your input with 'def' to demonstrate the storage feature of the UI."))
			return;
		sleep();
		printWithSleep("""	
				Great, we can now use this function whenever we want.
				Evaluate the function that you have stored by using the character that you stored in place of the underscore, e.g. 'eval f x=2'.
				""");
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'."))
			return;
		sleep();
		printWithSleep("""		
				Good. Here are some tips for using the storage feature:
				If you define a function to character that is already used, the old function will be overwritten.
				You can see all your function characters by typing in 'printfunctions' or 'pf'.
				You can also remove a function by using the 'removefunction' or 'rmf' command, or remove all functions by using 'clearfunctions'.
				Additionally to functions, you can also define constants using 'defc', 'defcon', or 'defconstant'. E.g. 'defc g 9.81'.
				The constants 'e' and 'pi' are already predefined.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.SIMP;
		runTillNext();
	}

	private static void simp() {
		printWithSleep("""
				Now, one of the most important features of our CAS is its ability to simplify expressions.
				For example take the expression:
				x^2*(0*ln(x)+(2*1)/x)
				This expression looks complicated, right?
				Let's feed it through the CAS:
				>>> def d x^2*(0*ln(x)+(2*1)/x)
				""" +
				KeywordInterface.safeKeywords("def d x^2*(0*ln(x)+(2*1)/x)")
				+ """
						
				Wow, quite the improvement.
				The CAS automatically simplified trivial steps like multiplication by 1 or adding 0.
				For those who are curious, that was the general formula of the derivative of 'f(x)^g(x)' applied to 'x^2'.
				If you want to explicitly simplify a function, use 'simp _' for example. 
				Also, if you want to define a function and simplify it in one step, you can use the 'defs' or 'deffunctionsimplify'.
				These explicit calls to simplify will perform extra simplifications such as distributing across addition.  
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.VAR;
		runTillNext();
	}

	private static void var() {
		printWithSleep("""
				Until this point, all the expressions that we've been using have only contained one variable.
				We will now construct a function using more than one variable.
				Define a new function with several different variables using the 'def' command, e.g. 'def g xy^2+q'.
				""");
		if (!tryInput(s -> "def".equals(s.substring(0, 3)), "Begin your input with 'def' or 'defs' to demonstrate the storage feature of the UI."))
			return;
		sleep();
		printWithSleep("""
				Now, when evaluating, you will need to specify each value, like 'eval x-y x=2 y=3'.
				Try evaluating your multivariable function using 'eval', e.g. 'eval g x=2 y=4 q=e.
				""", 2);
		if (!tryInput(s -> s.length() > 4 && "eval ".equals(s.substring(0, 5)), "Begin your input with 'eval'"))
			return;
		sleep();
		printWithSleep("Continue testing this feature, or type 'next' to continue.");
		currentState = DemoState.SUB;
		runTillNext();
	}

	private static void sub() {
		printWithSleep("""
				Now, with several variables, we can begin to substitute.
				It is probably best to explain with an example:
				>>> sub x^y y=sin(x)
				""" +
				KeywordInterface.safeKeywords("sub x^y y=sin(x)")
				+ """
				  
				As you can see, this replaces all 'y's in the function with 'sin(x)'.
				To try it yourself, take your multivariable function from before and replace a variable with a function using 'sub'.
				""");
		if (!tryInput(s -> s.length() > 3 && "sub ".equals(s.substring(0, 4)), "Begin your input with 'sub' to demonstrate the substitution feature of the UI."))
			return;
		sleep();
		printWithSleep("""
				Congratulations, you have just substituted a variable for a function.
				An important thing to remembers is that these changes are not saved in the function.
				For example, to substitute every 'x' in 'g' with 'x^2', you would need to run 'def g sub g x=x^2'.
				Additionally, like 'def', if you want to substitute and simplify in one step, use 'subs' or 'substitutesimplify'.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.SA;
		runTillNext();
	}

	private static void sa() {
		printWithSleep("""
				Remember, that whenever you define a new function it gets stored in a variable.
				>>> def a x^2
				""" +
				KeywordInterface.safeKeywords("def a x^2")
				+ """
    
				Now, what happens when we define a new function 'b' using 'a' as a variable?
				Try it yourself, define a new function 'b' as a function of a, e.g. def b a+1
				""");
		if (!tryInput(s -> !"def b a".equals(s) && s.length() > 5 && "def b ".equals(s.substring(0, 6)), "Begin your input with 'def b ' to define a function as 'b'. Make sure your input isn't 'def b a'."))
			return;
		sleep();
		if (VariableTools.doesNotContainsVariable(KeywordInterface.parseStored("b"), "a")) {
			System.out.println("An issue has occurred when parsing your function for 'b'. Please report this to the developers. Defaulting to 'def b a+1'...");
			KeywordInterface.safeKeywords("def b a+1");
		}
		printWithSleep("""
				Notice that 'b' is a function of 'a', not of 'x', when we evaluate, we are going to have to use 'a=[value]'.
				>>> eval b a=2
				""" +
				KeywordInterface.safeKeywords("eval b a=2")
				+ """
    
				Now, try using substitute all, type in 'sa b'.
				""");
		if (!tryInput("sa b"::equals, "Your input must be 'sa b' to substitute all in 'b'."))
			return;
		sleep();
		printWithSleep("""
				>>> def b _
				""" +
				KeywordInterface.safeKeywords("def b _")
				+ """
    
				Notice how 'b' is now a function in terms of 'x'.
				Try evaluating it, you can now use 'x=[value]', using 'a=[value]' will result in a error, as 'a' is no longer a variable in 'b'.
				""");
		if (!tryInput(s -> s.length() > 6 && "eval b ".equals(s.substring(0, 7)), "Begin your input with 'eval b ' to evaluate 'b'."))
			return;
		sleep();
		printWithSleep("Continue testing this feature, or type 'next' to continue.");
		currentState = DemoState.QUOTES;
		runTillNext();
	}

	private static void quotes() {
		printWithSleep("""
				By now, you may have encounter a situation that required outputs of command to be inputs to other commands,
				For example
				>>> sub x^2 x simp y+y
				This would return an error at the moment.
				To fix this, we put quotation marks around the whole simplification:
				>>> sub x^2 x "simp y+y"
				""" +
				KeywordInterface.safeKeywords("sub x^2 x=\"simp y+y\"")
				+ """
    
				Additionally, nested quotation marks do not work.
				If your command requires nested quotes, we suggest that you split it up onto several lines.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.PD;
		runTillNext();
	}

	private static void pd() {
		printWithSleep("""
				Another feature of this CAS is its ability to compute the partial derivative of any function.
				Some examples:
				>>> pd x x^2
				""" +
				KeywordInterface.safeKeywords("pd x x^2")
				+ """
    
				>>> pd x e^(sin(x)+y)
				""" +
				KeywordInterface.safeKeywords("pd x e^(sin(x)+y)")
				+ """
    
				>>> pd x x^(x^(x^x))
				""" +
				KeywordInterface.safeKeywords("pd x x^(x^(x^x))")
				+ """
    
				>>> pd y logb_{x^y+z}(2sin(y))
				""" +
				KeywordInterface.safeKeywords("pd y logb_{x^y+z}(2sin(y))")
				+ """
    
				Now try it yourself using the 'pd' command, in the syntax 'pd [variable] [function]'.
				""");
		if (!tryInput(s -> s.length() > 1 && "pd".equals(s.substring(0, 2)), "Begin your input with 'pd' to demonstrate the derivative feature of the UI."))
			return;
		sleep();
		printWithSleep("""
				Great, you have taken the derivative of a function.
				The 'pd' command in the command line interface is not the only way to evaluate derivatives.
				This CAS also supports the actual derivative function, denoted 'd/dx' (if it is with respect to x).
				The function 'd/dx(x^2)' will get parsed internally to 'd/dx[x^2]' and not to '2x', so to evaluate the derivative you must execute 'simp _'.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.TAY;
		runTillNext();
	}

	private static void tay() {
		printWithSleep("""
				One application of the derivative is in the form of a taylor series.
				The syntax for creating a taylor series goes as follows:
				>>> tay [function] [degree] [center].
				Try creating a taylor series using the 'tay' command.
				""");
		if (!tryInput(s -> "tay ".equals(s.substring(0, 4)), "Begin your input with 'tay' to demonstrate the taylor series feature of the CAS."))
			return;
		sleep();
		printWithSleep("""
				Good. Taylor series can often be used as good approximations to difficult functions.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.INT;
		runTillNext();
	}

	private static void ints() {
		printWithSleep("""
				Integration is the other great pillar of calculus.
				Currently, this CAS can only symbolically solve integrals of the form ∫[c*op(u)*u']du.
				More general symbolic integration will be added in future releases.
				An example where integration works is as follows:.
				>>> int 3*(cos(e^x))^2*sin(e^x)*e^x dx
				""" +
				KeywordInterface.safeKeywords("int 3*(cos(e^x))^2*sin(e^x)*e^x dx")
				+ """
    
				Try integration for yourself using the 'int' command.
				""");
		if (!tryInput(s -> s.length() > 3 && "int ".equals(s.substring(0, 4)), "Begin your input with 'int' to demonstrate the integration feature of the CAS."))
			return;
		sleep();
		printWithSleep("""
				Congratulations, you've just integrated your first function.
				Just like derivatives, the 'int' command is not the only way to evaluate integrals in this CAS.
				The internal representation of an integral is denoted 'int'. 
				To input an integral, you could parse 'int(2x)dx', but this would not be directly converted to 'int[2x]dx' internally.
				Integral parsing uses intermediary differentials, which will error if evaluation is attempted.
				To solve parsing issues, then execute the integral, you can still input 'simp _'.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.INTN;
		runTillNext();
	}

	private static void intn() {
		printWithSleep("""
				Despite the fact that not all functions are symbolically integrable, it is still almost always possible to find a numeric solution between two bounds.
				This CAS uses Simpson's Rule to be able to perform very accurate numeric integration.
				The syntax for numeric integration is 'intn [function] [startvalue] [endvalue]'.
				Try it out yourself using any function.
				""");
		if (!tryInput(s -> "intn ".equals(s.substring(0, 5)), "Begin your input with 'intn' to demonstrate the numeric integration feature of the CAS."))
			return;
		sleep();
		printWithSleep("""
				Good job!
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.SOL;
		runTillNext();
	}

	private static void sol() {
		printWithSleep("""
				In addition to numerical integration, this CAS also implements numerical root finding.
				The system we use to find zeroes requires a specified start and end to look for roots in.
				The syntax is 'sol [function] [startrange] [endrange]'.
				Try it out yourself:
				""");
		if (!tryInput(s -> s.length() > 3 && "sol ".equals(s.substring(0, 4)), "Begin your input with 'sol' to demonstrate the solving feature of the CAS."))
			return;
		sleep();
		printWithSleep("""
				As with all numeric methods, there are some quirks.
				Very often, simple solutions such as 1 will be found as a decimal like 1.000000000023.
				There are some settings like exit conditions that change how the solver works which may help fix minor issues.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.EXT;
		runTillNext();
	}

	private static void ext() {
		printWithSleep("""
				With the ability to solve for zeros, we can now find extrema of functions.
				For example:
				>>> ext max 1-x^2 -2 2.
				""" +
				KeywordInterface.safeKeywords("ext max 1-x^2 -2 2")
				+ """
    
				This command found the maximum of '1-x^2' on the range (-2, 2).
				There are other commands in addition to just max.
				The 5 options after 'ext' are 'min, max, anymin, anymax, inflection'.
				min and max return the maximum or minimum of the function in the given range.
				anymin, anymax, and inflection return an array of all local minima, maxima, or inflection points.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.SET;
		runTillNext();
	}

	@SuppressWarnings("SpellCheckingInspection")
	private static void set() {
		printWithSleep("""
				Now, you learn how to view or modify settings.
				Type in 'printsettings' or 'ps' to see all current settings.
				""");
		if (!tryInput(s -> "printsettings".equals(s) || "ps".equals(s), "Type in 'settings' to see the settings."))
			return;
		sleep();
		printWithSleep("""
				These are all the current settings.
				If you want to change a setting, use 'setsetting [setting] [value]', or the shortcut 'ss'.
				As you saw, there are a lot of settings. If you want to learn what a setting does, we recommend reading our documentation.
				Default settings are read from 'config/cas.properties' on launch. This file also contains documentation regarding the effects of various settings.
				Continue testing this feature, or type 'next' to continue.
				""");
		currentState = DemoState.LATEX;
		runTillNext();
	}

	private static void latex() {
		printWithSleep("""
				The custom parser used by this CAS internally operates on a system with formatting modeled after LaTeX.
				In some edge cases, the parser may have difficulty converting raw input to this LaTeX-like form, resulting in an 'unsupported' error.
				If you are familiar with LaTeX, it is highly recommended you enable the setting 'enforceEscapes' both in the runtime settings (with 'ss') and in 'config/cas.properties'.
				This setting will disable the raw-to-LaTeX conversion, increasing both performance and consistency.
				Using this feature requires all input to be LaTeX-escaped, so expressions like 'sin(pi*x)' should be written '\\sin(\\pi*x)'.
				If you would like to enable this feature now, do so using 'ss enforceEscapes true' and then test the new functionality. Type 'next' to continue.
				""");
		currentState = DemoState.END;
		runTillNext();
	}

	private static void end() {
		printWithSleep("""
				Thank you for completing the demo.
				For more details such as more features and shortcuts in the UI, check out our documentation or give the code a read.
				We highly encourage just playing around to see what you can do.
				The demo will now exit. We hope you enjoy CASprzak!
				""");
		currentState = DemoState.EXIT;
	}
}

