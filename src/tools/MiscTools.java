package tools;

import functions.GeneralFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import tools.helperclasses.Pair;

import java.util.LinkedList;
import java.util.List;

public class MiscTools {

	private MiscTools(){}

	/**
	 * Returns n factorial (n!)
	 * @param n the number
	 * @return n!
	 */
	public static long factorial(int n) {
		if (n < 0)
			throw new UnsupportedOperationException("Cannot take the factorial of a negative number.");
		else if (n <= 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * Returns the location of a {@link GeneralFunction} in its class-based sort order (see {@link GeneralFunction#sortOrder})
	 * @param function the function whose location in the class order is to be found
	 * @return location in {@link GeneralFunction#sortOrder}
	 */
	public static int findClassValue(GeneralFunction function) {
		Class<?> functionClass = function.getClass();
		for (int i = 0; i < GeneralFunction.sortOrder.length; i++) {
			if (GeneralFunction.sortOrder[i].isAssignableFrom(functionClass))
				return i;
		}
		throw new UnsupportedOperationException("Class " + function.getClass().getSimpleName() + " not supported.");
	}

	/**
	 * Returns a list of the elements in this {@link Sum} with the constants stripped as a pair. Ex: {@code x^2+2sin(x)} becomes {@code [<1.0, x^2>, <2.0, sin(x)>]}
	 * @param sum the sum whose constants should be stripped
	 * @return the list of pairs as specified above
	 */
	public static List<Pair<Double, GeneralFunction>> stripConstantsOfSum(Sum sum) {
		GeneralFunction[] sumArray = sum.getFunctions();
		List<Pair<Double, GeneralFunction>> strippedPairsArray = new LinkedList<>();
		for (GeneralFunction function: sumArray) {
			if (function instanceof Product multiply) {
				GeneralFunction[] terms = multiply.getFunctions();
				if (terms[0] instanceof Constant constant)
					strippedPairsArray.add(new Pair<>(constant.constant, new Product(ArrayTools.removeFunctionAt(terms, 0)).simplifyTrivialElement()));
				else
					strippedPairsArray.add(new Pair<>(1.0, multiply));
			} else {
				strippedPairsArray.add(new Pair<>(1.0, function));
			}
		}
		return strippedPairsArray;
	}

}
