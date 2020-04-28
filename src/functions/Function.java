package functions;

import config.Settings;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.UnitaryFunction;
import org.jetbrains.annotations.NotNull;
import tools.MiscTools;

import java.util.HashMap;
import java.util.Map;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Comparable<Function> {

	/**
	 * Describes the order that a {@link Function} should appear in a sorted array (used in {@link #compareTo(Function)})
	 */
	@SuppressWarnings("ClassReferencesSubclass")
	public static final Class<?>[] sortOrder = {
			Constant.class,
			Variable.class,
			Multiply.class,
			Pow.class,
			Logb.class,
			UnitaryFunction.class,
			Add.class
	};
	/**
	 * Caches derivatives with the key corresponding to the varID of the derivative
	 */
	protected final HashMap<Character, Function> derivatives = new HashMap<>();

	/**
	 * Returns a String representation of the Function
	 * @return String representation of function
	 */
	public abstract String toString();

	/**
	 * Returns a clone of the {@link Function}
	 * @return a clone of the Function
	 */
	public abstract Function clone();

	/**
	 * Simplifies a {@link Function} multiple times
	 * @param times the amount of times it is simplified
	 * @return the simplified {@link Function}
	 */
	public Function simplifyTimes(int times) {
		Function newFunction, currentFunction = this;
		for (int i = 0; i < times; i++) {
			newFunction = currentFunction.simplify();
			if (newFunction.toString().equals(currentFunction.toString()))
				return newFunction;
			currentFunction = newFunction;
		}
		return currentFunction;
	}

	/**
	 * Returns the derivative of the function, simplified
	 * @param varID the ID of the variable being differentiated
	 * @return the derivative of the {@link Function} it is called on, simplified
	 */
	public Function getSimplifiedDerivative(char varID) {
		if (Settings.cacheDerivatives && derivatives.containsKey(varID))
			return derivatives.get(varID);
		Function derivative = getDerivative(varID).simplify();
		if (Settings.cacheDerivatives)
			derivatives.put(varID, derivative);
		return derivative;
	}


	/**
	 * Returns the Nth derivative of the function, simplified
	 * @param varID the ID of the variable being differentiated
	 * @param N     the amount of times to differentiate
	 * @return the Nth derivative of the {@link Function} it is called on, simplified
	 */
	public Function getNthDerivative(char varID, int N) {
		Function currentFunction = this;
		while (N > 0) {
			currentFunction = currentFunction.getSimplifiedDerivative(varID);
			N--;
		}
		return currentFunction;
	}

	/**
	 * Returns the value of the derivative at point
	 * @param varID the ID of the variable being differentiated
	 * @param point the point to find the derivative at
	 * @return the value of the derivative at point
	 */
	@SuppressWarnings("unused")
	public double derivativeAt(char varID, Map<Character, Double> point) {
		return getDerivative(varID).evaluate(point);
	}

	/**
	 * Substitutes a new {@link Function} into a variable
	 * @param varID     the variable to be substituted into
	 * @param toReplace the {@link Function} that will be substituted
	 * @return the new {@link Function} after all substitutions are preformed
	 */
	public abstract Function substitute(char varID, Function toReplace);

	/**
	 * Returns true when the two functions simplified are equal
	 * @param that The {@link Function} that the current function is being checked equal to
	 * @return true when the two functions are equal
	 */
	public abstract boolean equals(Function that);

	/**
	 * Simplifies the two functions, then compares them with {@link #equals(Function)}
	 * @param that the object compared to
	 * @return true if they're equal
	 */
	public boolean equals(Object that) {
		if (!(that instanceof Function))
			return false;
		return this.simplify().equals(((Function) that).simplify());
	}

	/**
	 * Used internally for comparing two functions of **the same exact type**
	 * @param that the {@link Function} compared to
	 * @return comparison
	 */
	protected abstract int compareSelf(Function that);

	/**
	 * Two different Function types are sorted according to {@link #sortOrder} and {@link MiscTools#findClassValue(Function)}, and same types are sorted using {@link #compareSelf(Function)}
	 * @param that the {@link Function} compared to
	 * @return comparison
	 */
	public int compareTo(@NotNull Function that) {
		if (this.equals(that))
			return 0;
		else if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		else
			return (MiscTools.findClassValue(this) - MiscTools.findClassValue(that));
	}
}
