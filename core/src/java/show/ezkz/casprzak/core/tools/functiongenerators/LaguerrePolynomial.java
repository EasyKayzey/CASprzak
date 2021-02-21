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

import static show.ezkz.casprzak.core.tools.MiscTools.factorial;

/**
 * The methods in {@link LaguerrePolynomial} deal with <a href="https://en.wikipedia.org/wiki/Laguerre_polynomials">Laguerre Polynomials</a>.
 */
public class LaguerrePolynomial {

	private static final Map<Integer, GeneralFunction> cache = new HashMap<>();

	/**
	 * Returns the nth Laguerre polynomial with the default variable defined with {@link Settings#singleVariableDefault}
	 * @param n the nth polynomial
	 * @return nth Laguerre polynomial
	 */
	public static GeneralFunction laguerrePolynomial(int n) {
		if (Settings.cacheDerivatives && cache.containsKey(n))
			return cache.get(n);

		GeneralFunction[] sum = new GeneralFunction[n+1];
		for (int k = 0; k <= n; k++) {
			sum[k] = new Product(new Constant(constant(n, k)), new Pow(new Constant(k), new Variable(Settings.singleVariableDefault))).simplify();
		}
		GeneralFunction polynomial = new Sum(sum).simplify();
		cache.put(n, polynomial);
		return polynomial;
	}

	/**
	 * Returns the nth Laguerre polynomial with a variable of the specified String
	 * @param n the nth polynomial
	 * @param variable the String of the {@link Variable} of the polynomial
	 * @return nth Laguerre polynomial
	 */
	public static GeneralFunction laguerrePolynomial(int n, String variable) {
		GeneralFunction polynomial = laguerrePolynomial(n);
		if (Settings.singleVariableDefault.equals(variable))
			return polynomial;
		else {
			Map<String, Variable> substitution = new HashMap<>() {
				{
					put(Settings.singleVariableDefault, new Variable(variable));
				}
			};
			return polynomial.substituteVariables(substitution);
		}
	}

	private static double constant(int n, int k) {
		double mult = 1;
		for (int i = 0; i < n-k; i++) {
			mult *= n - i;
		}
		mult /= factorial(k);
		mult /= factorial(n-k);
		mult *= (1 - 2 * (k % 2));
		return mult;
	}

}
