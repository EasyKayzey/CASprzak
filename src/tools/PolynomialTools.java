package tools;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;

import java.util.function.DoublePredicate;

@SuppressWarnings("ChainOfInstanceofChecks")
public class PolynomialTools {

	/**
	 * Checks if a given {@link Function} is a polynomial. Must call {@link Function#simplify()} before using.
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
	 * Checks if a given {@link Function} is a monomial (positive integer powers). Must call {@link Function#simplify()} before using.
	 * @param function the function to be checked
	 * @return true if function is a monomial
	 */
	public static boolean isMonomial(Function function) {
		return isGivenMonomial(function, (a -> ((int) a == a && a > 0)));
	}

	/**
	 * Checks if a given {@link Function} is a generalized monomial (any constant powers). Must call {@link Function#simplify()} before using.
	 * @param function the function to be checked
	 * @return true if function is a generalized monomial
	 */
	public static boolean isGeneralMonomial(Function function) {
		return isGivenMonomial(function, a -> true);
	}

	private static boolean isGivenMonomial(Function function, DoublePredicate test) {
		if (function instanceof Constant || function instanceof Variable || (function instanceof Pow pow && pow.getFunction2() instanceof Variable && pow.getFunction1() instanceof Constant exp && test.test(exp.evaluate(null)))) {
			return true;
		} else if (function instanceof Multiply product) {
			Function[] elements = product.getFunctions();
			for (Function element : elements) {
				if (!(element instanceof Constant || element instanceof Variable || element instanceof Pow))
					return false;
				if (element instanceof Pow pow && !(pow.getFunction2() instanceof Variable && pow.getFunction1() instanceof Constant exp && test.test(exp.evaluate(null))))
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

	//TODO WRITE AND DOCUMENT THIS
	public static double getDegree(Function function) {
		if (!isMonomial(function))
			throw new IllegalArgumentException(function + " is not a monomial");
		else if (function instanceof Variable)
			return 1;
		else if (function instanceof Pow power && power.getFunction1() instanceof Constant number)
			return number.constant;
		else if (function instanceof Multiply product) {
			Function[] elemennts = product.getFunctions();
			double sum = 0;
			for (Function element: elemennts) {
				if (element instanceof Pow power && power.getFunction1() instanceof Constant number)
					sum += number.constant;
			}
			return sum;
		} else {
			throw new IllegalArgumentException("This code should never run");
		}
	}
}
