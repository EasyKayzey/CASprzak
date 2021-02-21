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

	public static GeneralFunction legrendePolynomial(int n) {
		if (Settings.cacheDerivatives && cache.containsKey(n))
			return cache.get(n);

		int p = n % 2 == 0 ? n/2 : (n+1)/2;
		GeneralFunction[] sum = new GeneralFunction[n-p+1];
		for (int m = p; m <= n ; m++) {
			sum[m-p] = new Product(new Constant(((n-m) % 2 == 0 ? 1 : -1) * constant(n,m)), new Pow(new Constant(2*m-n), new Variable(Settings.singleVariableDefault))).simplify();
		}
		GeneralFunction polynomial = new Sum(sum).simplify();
		cache.put(n, polynomial);
		return polynomial;
	}

	public static GeneralFunction legrendePolynomial(int n, String variable) {
		GeneralFunction polynomial = legrendePolynomial(n);
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

	public static GeneralFunction normalLegrendePolynomial(int n) {
		return new Product(new Constant(1/normalizingConstantDouble(n)), legrendePolynomial(n)).simplify();
	}

	public static GeneralFunction normalLegrendePolynomial(int n, String variable) {
		return new Product(new Constant(1/normalizingConstantDouble(n)), legrendePolynomial(n, variable)).simplify();
	}

	public static GeneralFunction normalizingConstant(int n) {
		return new Constant(normalizingConstantDouble(n));
	}

	private static double normalizingConstantDouble(int n) {
		return 1 / sqrt(0.5 * (2*n+1));
	}

	private static double constant(int n, int m) {
		double mult = 1;
		for (int i = 0; i < n; i++) {
			mult *= ((2*m - i)/2.);
		}
		mult /= factorial(m);
		mult /= factorial(n-m);
		return mult;
	}

}
