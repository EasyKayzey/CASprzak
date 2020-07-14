package tools;

import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.endpoint.Constant;
import functions.endpoint.Variable;

import java.util.Map;
import java.util.function.DoublePredicate;

/**
 * The {@link PolynomialTools} class contains miscellaneous methods for {@link GeneralFunction}s which are polynomials.
 * {@link GeneralFunction#simplify()} MUST be called on the inputs to ALL methods in this class before execution for accurate results to be returned.
 */
@SuppressWarnings("ChainOfInstanceofChecks")
public class PolynomialTools {

	/**
	 * Checks if a given {@link GeneralFunction} is a polynomial.
	 * @param function the function to be checked
	 * @return true if the function is a polynomial
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
	 * Checks if a given {@link GeneralFunction} is a monomial (positive integer powers).
	 * @param function the function to be checked
	 * @return true if the function is a monomial
	 */
	public static boolean isMonomial(GeneralFunction function) {
		return isGivenMonomial(function, (a -> (ParsingTools.isAlmostInteger(a) && a > 0)));
	}

	/**
	 * Checks if a given {@link GeneralFunction} is a generalized monomial (any constant powers).
	 * @param function the function to be checked
	 * @return true if the function is a generalized monomial
	 */
	public static boolean isGeneralMonomial(GeneralFunction function) {
		return isGivenMonomial(function, a -> true);
	}

	private static boolean isGivenMonomial(GeneralFunction function, DoublePredicate test) {
		if (function instanceof Constant || function instanceof Variable || (function instanceof Pow pow && pow.getFunction2() instanceof Variable && pow.getFunction1() instanceof Constant exp && test.test(exp.evaluate(Map.of())))) {
			return true;
		} else if (function instanceof Product product) {
			GeneralFunction[] elements = product.getFunctions();
			for (GeneralFunction element : elements) {
				if (!(element instanceof Constant || element instanceof Variable || element instanceof Pow))
					return false;
				if (element instanceof Pow pow && !(pow.getFunction2() instanceof Variable && pow.getFunction1() instanceof Constant exp && test.test(exp.evaluate(Map.of()))))
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the degree of a monomial.
	 * @param monomial The monomial whose degree is being found
	 * @return the degree of the monomial
	 * @throws IllegalArgumentException when the input is not a monomial
	 */
	public static double getDegree(GeneralFunction monomial) throws IllegalArgumentException {
		if (!isGeneralMonomial(monomial))
			throw new IllegalArgumentException(monomial + " is not a monomial");
		else if (monomial instanceof Variable)
			return 1;
		else if (monomial instanceof Pow power && power.getFunction1() instanceof Constant number)
			return number.constant;
		else if (monomial instanceof Product product) {
			GeneralFunction[] elements = product.getFunctions();
			double sum = 0;
			for (GeneralFunction element : elements) {
				if (element instanceof Pow power && power.getFunction1() instanceof Constant number)
					sum += number.constant;
				else if (element instanceof Variable)
					sum += 1;
			}
			return sum;
		} else {
			throw new IllegalStateException("Monomial " + monomial + " is not a Variable, Product, or Power, but does not satisfy isGeneralMonomial.");
		}
	}
}
