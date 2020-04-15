package tools;

public class MiscTools {
	/**
	 * Returns n factorial (n!)
	 *
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
}
