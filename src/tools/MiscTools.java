package tools;

import config.Settings;
import functions.GeneralFunction;
import parsing.LatexReplacer;

import java.lang.reflect.MalformedParametersException;

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
	 * @param function the function whose class order is to be found
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

}
