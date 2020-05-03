package tools;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Sum;
import functions.commutative.Product;
import functions.special.Constant;
import functions.special.Variable;

import java.util.function.DoublePredicate;

@SuppressWarnings("ChainOfInstanceofChecks")
public class PolynomialTools {

	/**
	 * Checks if a given {@link GeneralFunction} is a polynomial. Must call {@link GeneralFunction#simplify()} before using.
	 * @param function the function to be checked
	 * @return true if function is a polynomial
	 */
	public static boolean isPolynomial(GeneralFunction function) {
		if (isMonomial(function)) {
			return true;
		} else if (function instanceof Sum sum) {
			GeneralFunction[] terms = sum.getFunctions();
			for (GeneralFunction term : terms) {
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
	 * Checks if a given {@link GeneralFunction} is a monomial (positive integer powers). Must call {@link GeneralFunction#simplify()} before using.
	 * @param function the function to be checked
	 * @return true if function is a monomial
	 */
	public static boolean isMonomial(GeneralFunction function) {
		return isGivenMonomial(function, (a -> ((int) a == a && a > 0)));
	}

	/**
	 * Checks if a given {@link GeneralFunction} is a generalized monomial (any constant powers). Must call {@link GeneralFunction#simplify()} before using.
	 * @param function the function to be checked
	 * @return true if function is a generalized monomial
	 */
	public static boolean isGeneralMonomial(GeneralFunction function) {
		return isGivenMonomial(function, a -> true);
	}

	private static boolean isGivenMonomial(GeneralFunction function, DoublePredicate test) {
		if (function instanceof Constant || function instanceof Variable || (function instanceof Pow pow && pow.getFunction2() instanceof Variable && pow.getFunction1() instanceof Constant exp && test.test(exp.evaluate(null)))) {
			return true;
		} else if (function instanceof Product product) {
			GeneralFunction[] elements = product.getFunctions();
			for (GeneralFunction element : elements) {
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

	/**
	 * Returns the degree a monomial.
	 * @param function The monomial whose degree is being found
	 * @return the degree of the monomial
	 * @throws IllegalArgumentException when input is not a monomial
	 */
	public static double getDegree(GeneralFunction function) throws IllegalArgumentException {
		if (!isGeneralMonomial(function))
			throw new IllegalArgumentException(function + " is not a monomial");
		else if (function instanceof Variable)
			return 1;
		else if (function instanceof Pow power && power.getFunction1() instanceof Constant number)
			return number.constant;
		else if (function instanceof Product product) {
			GeneralFunction[] elements = product.getFunctions();
			double sum = 0;
			for (GeneralFunction element: elements) {
				if (element instanceof Pow power && power.getFunction1() instanceof Constant number)
					sum += number.constant;
				else if (element instanceof Variable)
					sum += 1;
			}
			return sum;
		} else {
			throw new IllegalStateException("This code should never run");
		}
	}
}
