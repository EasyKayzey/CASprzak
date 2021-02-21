package show.ezkz.casprzak.core.tools.functiongenerators;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;
import static show.ezkz.casprzak.core.tools.MiscTools.*;

/**
 * The methods in {@link LegrendePolynomial} deal with <a href="https://en.wikipedia.org/wiki/Legendre_polynomials">Legrende Polynomials</a>.
*/
public class LegrendePolynomial {

	private static final Map<Integer, GeneralFunction> cache = new HashMap<>();
	private static final String defaultVariable = "\\var";

	private static GeneralFunction makeLegrendePolynomial(int n) {
		if (cache.containsKey(n))
			return cache.get(n);

		int p = (n + 1) / 2;
		GeneralFunction[] sum = new GeneralFunction[n - p + 1];
		for (int m = p; m <= n; m++) {
			sum[m - p] = new Product(new Constant(constant(n, m)), new Pow(new Constant(2 * m - n), new Variable(defaultVariable))).simplify();
		}
		GeneralFunction polynomial = new Sum(sum).simplify();

		if (Settings.cacheLegrendePolynomials)
			cache.put(n, polynomial);
		return polynomial;
	}

	/**
	 * Returns the nth Legrende polynomial with the default variable defined with {@link Settings#singleVariableDefault}
	 * @param n the nth polynomial
	 * @return nth Legrende polynomial
	 */
	public static GeneralFunction legrendePolynomial(int n) {
		Map<String, Variable> substitution = new HashMap<>();
		substitution.put(defaultVariable, new Variable(Settings.singleVariableDefault));
		return makeLegrendePolynomial(n).substituteVariables(substitution);
	}

	/**
	 * Returns the nth Legrende polynomial with a variable of the specified String
	 * @param n the nth polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return nth Legrende polynomial
	 */
	public static GeneralFunction legrendePolynomial(int n, String variable) {
		Map<String, Variable> substitution = new HashMap<>();
		substitution.put(defaultVariable, new Variable(variable));
		return makeLegrendePolynomial(n).substituteVariables(substitution);
	}

	/**
	 * Returns the nth normalized Legrende polynomial with the default variable defined with {@link Settings#singleVariableDefault}
	 * @param n the nth polynomial
	 * @return nth normalized Legrende polynomial
	 */
	public static GeneralFunction normalLegrendePolynomial(int n) {
		return new Product(new Constant(1 / normalizingConstantDouble(n)), legrendePolynomial(n)).simplify();
	}

	/**
	 * Returns the nth normalized Legrende polynomial with a variable of the specified String
	 * @param n the nth polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return nth normalized Legrende polynomial
	 */
	public static GeneralFunction normalLegrendePolynomial(int n, String variable) {
		return new Product(new Constant(1 / normalizingConstantDouble(n)), legrendePolynomial(n, variable)).simplify();
	}

	/**
	 * The normalizing constant of the nth Legrende polynomial
	 * ie If you wanted to normalize, you would do {@code 1/N * L_n}
	 * @param n the nth polynomial
	 * @return normalizing constant of the nth Legrende polynomial
	 */
	public static GeneralFunction normalizingConstant(int n) {
		return new Constant(normalizingConstantDouble(n));
	}

	private static double normalizingConstantDouble(int n) {
		return 1 / sqrt(0.5 * (2 * n + 1));
	}

	private static double constant(int n, int m) {
		double mult = 1;
		for (int i = 0; i < n; i++) {
			mult *= ((2 * m - i) / 2.);
		}
		mult /= factorial(m);
		mult /= factorial(n - m);
		mult *= 1 - 2 * ((n - m) % 2); // (-1)^(n-m)
		return mult;
	}

}
