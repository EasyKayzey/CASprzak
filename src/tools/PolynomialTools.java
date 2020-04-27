package tools;

import functions.Function;
import functions.commutative.Add;

public class PolynomialTools {

	/**
	 * Checks if a given {@link Function} is a polynomial
	 * @param function the function to be checked
	 * @return true if function is a polynomial
	 */
	public static boolean isPolynomial(Function function) {
		if (isMonomial(function)) {
			return true;
		} else if (function instanceof Add sum) {
			Function[] terms = sum.getFunctions();
			for (Function term : terms) {
				if (!isMonomial(term)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if a given {@link Function} is a monomial
	 * @param function the function to be checked
	 * @return true if function is a monomial
	 */
	public static boolean isMonomial(Function function) {
		return false;
	}
}
