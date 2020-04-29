package tools;

import functions.Function;

public class MiscTools {

	private MiscTools(){}

	/**
	 * Returns n factorial (n!)
	 * @param n the number
	 * @return n!
	 */
	public static int factorial(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Cannot take the factorial of a negative number.");
		else if (n <= 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * Returns the location of a {@link Function} in its class-based sort order (see {@link Function#sortOrder})
	 * @param function the function whose class order is to be found
	 * @return location in {@link Function#sortOrder}
	 */
	public static int findClassValue(Function function) {
		Class<?> functionClass = function.getClass();
		for (int i = 0; i < Function.sortOrder.length; i++) {
			if (Function.sortOrder[i].isAssignableFrom(functionClass))
				return i;
		}
		throw new IllegalArgumentException("Class " + function.getClass().getSimpleName() + " not supported.");
	}

	/**
	 * Parses a string to a boolean using the following rules, ignoring case:
	 * TRUE: true, 1, yes, t
	 * FALSE: false, 0, no, f
	 * @param s the string to be parsed
	 * @return the string parsed to a boolean
	 */
	public static boolean parseBoolean(String s) {
		return switch (s.toLowerCase()) {
			case "true", "t", "1", "yes" -> true;
			case "false", "f", "0", "no" -> false;
			default -> throw new IllegalArgumentException(s + " cannot be parsed to a boolean");
		};
	}
}
