package core.tools.functiongenerators;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.functions.endpoint.Variable;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;
import static java.lang.Math.pow;
import static core.tools.MiscTools.factorial;
import static core.tools.defaults.DefaultFunctions.*;

/**
 * The methods in {@link HermitePolynomial} deal with <a href="https://en.wikipedia.org/wiki/Hermite_polynomials">Hermite Polynomials</a>.
 */
public class HermitePolynomial {

	private static final Map<Integer, GeneralFunction> cache = new HashMap<>();
	private static final String defaultVariable = "\\var";

	private static GeneralFunction makeHermitePolynomial(int n) {
		if (cache.containsKey(n))
			return cache.get(n);

		GeneralFunction[] sum = new GeneralFunction[n / 2 + 1];
		for (int m = 0; m <= n / 2; m++) {
			sum[m] = new Product(new Constant(constant(n, m)), new Pow(new Constant(n - 2 * m), new Variable(defaultVariable))).simplify();
		}
		GeneralFunction polynomial = new Sum(sum).simplify();
		if (Settings.cacheHermitePolynomials)
			cache.put(n, polynomial);
		return polynomial;
	}

	/**
	 * Returns the nth Hermite polynomial with the default variable defined with {@link Settings#singleVariableDefault}
	 * @param n the nth polynomial
	 * @return nth Hermite polynomial
	 */
	public static GeneralFunction hermitePolynomial(int n) {
		return hermitePolynomial(n, Settings.singleVariableDefault);
	}

	/**
	 * Returns the nth Hermite polynomial with a variable of the specified String
	 * @param n the nth polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return nth Hermite polynomial
	 */
	public static GeneralFunction hermitePolynomial(int n, String variable) {
		Map<String, Variable> substitution = new HashMap<>();
		substitution.put(defaultVariable, new Variable(variable));
		return makeHermitePolynomial(n).substituteVariables(substitution);
	}

	/**
	 * Returns the nth normalized Hermite polynomial with the default variable defined with {@link Settings#singleVariableDefault}
	 * @param n the nth polynomial
	 * @return nth normalized Hermite polynomial
	 */
	public static GeneralFunction normalHermitePolynomial(int n) {
		return new Product(reciprocal(normalizingConstant(n)), hermitePolynomial(n)).simplify();
	}

	/**
	 * Returns the nth normalized Hermite polynomial with a variable of the specified String
	 * @param n the nth polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return nth normalized Hermite polynomial
	 */
	public static GeneralFunction normalHermitePolynomial(int n, String variable) {
		return new Product(reciprocal(normalizingConstant(n)), hermitePolynomial(n, variable)).simplify();
	}

	/**
	 * The normalizing constant of the nth Hermite polynomial
	 * ie If you wanted to normalize, you would do {@code 1/N * L_n}
	 * @param n the nth polynomial
	 * @return normalizing constant of the nth Hermite polynomial
	 */
	public static GeneralFunction normalizingConstant(int n) {
		return sqrt(new Product(new Constant(pow(2, n) * factorial(n)), sqrt(PI))).simplify();
	}

	private static double constant(int n, int m) {
		double mult = 1;
		for (int i = 0; i < n - m; i++) {
			mult *= n - i;
		}
		for (int i = 0; i < n - 2 * m; i++) {
			mult *= 2. / (n - 2 * m - i);
		}
		mult *= (1 - 2 * (m % 2)); // (-1)^m
		return mult;
	}


}
