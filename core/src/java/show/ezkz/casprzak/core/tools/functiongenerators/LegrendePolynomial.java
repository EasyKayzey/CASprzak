package show.ezkz.casprzak.core.tools.functiongenerators;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.endpoint.Constant;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.*;

/**
 * The methods in {@link LegrendePolynomial} deal with <a href="https://en.wikipedia.org/wiki/Legendre_polynomials">Legrende Polynomials</a>.
*/
public class LegrendePolynomial {

	private final Map<Integer, GeneralFunction> cache = new HashMap<>();

	public GeneralFunction legrendePolynomial(int n) {

		return null;
	}

	public GeneralFunction normalLegrendePolynomial(int n) {

		return null;
	}

	public GeneralFunction normalizingConstant(int n) {
		return new Constant(normalizingConstantDouble(n));
	}

	private double normalizingConstantDouble(int n) {
		return 1 / sqrt(0.5 * (2*n+1));
	}

}
