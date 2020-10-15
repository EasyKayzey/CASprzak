package show.ezkz.casprzak.core.tools;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.CommutativeFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.core.tools.helperclasses.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings.minimal;

/**
 * The {@link MiscTools} class contains miscellaneous methods.
 */
public class MiscTools {

	private MiscTools(){}

	/**
	 * Returns n factorial (n!)
	 * @param n the number
	 * @return n!
	 */
	public static long factorial(int n) {
		if (n < 0)
			throw new UnsupportedOperationException("Cannot take the factorial of a negative number.");
		else if (n <= 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * Computes the GCD of the inputs. It is preferred to input {@code a > b}, but this is not explicitly necessary.
	 * @param a the first input
	 * @param b the second input
	 * @return the GCD of the inputs
	 */
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	/**
	 * Returns the location of a {@link GeneralFunction} in its class-based sort order (see {@link GeneralFunction#sortOrder})
	 * @param function the function whose location in the class order is to be found
	 * @return location in {@link GeneralFunction#sortOrder}
	 */
	public static int findClassValue(GeneralFunction function) {
		Class<?> functionClass = function.getClass();
		for (int i = 0; i < GeneralFunction.sortOrder.length; i++) {
			if (GeneralFunction.sortOrder[i].isAssignableFrom(functionClass))
				return i;
		}
		throw new UnsupportedOperationException("Class " + function.getClass().getSimpleName() + " not supported.");
	}

	/**
	 * Returns a list of the elements in this {@link Sum} with the constants stripped as a pair. Ex: {@code x^2+2sin(x)} becomes {@code [<1.0, x^2>, <2.0, sin(x)>]}
	 * @param sum the sum whose constants should be stripped
	 * @return the list of pairs as specified above
	 */
	public static List<Pair<Double, GeneralFunction>> stripConstantsOfSum(Sum sum) {
		GeneralFunction[] sumArray = sum.getFunctions();
		List<Pair<Double, GeneralFunction>> strippedPairsArray = new LinkedList<>();
		for (GeneralFunction function: sumArray) {
			if (function instanceof Product multiply) {
				GeneralFunction[] terms = multiply.getFunctions();
				if (terms[0] instanceof Constant constant)
					strippedPairsArray.add(new Pair<>(constant.constant, new Product(ArrayTools.removeFunctionAt(terms, 0)).simplifyTrivialElement(minimal)));
				else
					strippedPairsArray.add(new Pair<>(1.0, multiply));
			} else {
				strippedPairsArray.add(new Pair<>(1.0, function));
			}
		}
		return strippedPairsArray;
	}

	/**
	 * Executes {@link CommutativeFunction#simplifyTrivialElement(show.ezkz.casprzak.core.config.SimplificationSettings)} until the function is not a {@code CommutativeFunction} or has a argument count greater than one. Ex: {@code (((2*x)))} becomes {@code 2*x}
	 * @param function the function to be simplified
	 * @return the function with all layers removed
	 */
	public static GeneralFunction toFirstNonTrivial(GeneralFunction function) {
		while (function instanceof CommutativeFunction c && c.getFunctions().length <= 1)
			function = c.simplifyTrivialElement(minimal);
		return function;
	}

	/**
	 * Prints a message, sleeping for {@link Settings#defaultSleep} between newlines, but not at the end
	 * @param message the message to print
	 */
	public static void printWithSleep(String message) {
		printWithSleep(message, Settings.defaultSleep);
	}

	/**
	 * Prints a message, sleeping for {@code time} seconds between newlines, but not at the end
	 * @param message the message to print
	 * @param time amount of seconds to sleep
	 */
	public static void printWithSleep(String message, double time) {
		String[] lines = ParsingTools.newline.split(message);
		for (int i = 0; i < lines.length - 1; i++) {
			System.out.println(lines[i]);
			sleep(time);
		}
		System.out.println(lines[lines.length - 1]);
	}

	/**
	 * Prints a message, sleeping for {@link Settings#defaultSleep} between newlines, then sleeps that same amount at the end if {@code sleepAtEnd} is true.
	 * @param message the message to print
	 * @param sleepAtEnd denotes whether the message should end with a {@code sleep()}
	 */
	public static void printWithSleep(String message, boolean sleepAtEnd) {
		printWithSleep(message, Settings.defaultSleep);
		if (sleepAtEnd)
			sleep();
	}

	/**
	 * Sleeps for {@link Settings#defaultSleep} seconds
	 */
	public static void sleep() {
		try {
			TimeUnit.MILLISECONDS.sleep((long) (Settings.defaultSleep*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sleeps for {@code seconds} seconds
	 * @param seconds the amount of seconds to sleep for
	 */
	public static void sleep(double seconds) {
		try {
			TimeUnit.MILLISECONDS.sleep((long) (seconds*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Simplifies the input without executing any of the optional simplifications steps enabled and disabled in {@link Settings}
	 * @param function the function to be minimally simplified
	 * @return the minimally simplified version of the function
	 */
	public static GeneralFunction minimalSimplify(GeneralFunction function) {
		return function.simplify(minimal);
	}
}
