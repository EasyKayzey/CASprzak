package functions;

import core.ArrLib;
import core.Parser;
import core.Settings;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.UnitaryFunction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Comparable<Function> {

	/**
	 * Caches derivatives with the key corresponding to the varID of the derivative
	 */
	protected final HashMap<Integer, Function> derivatives = new HashMap<>();

	/**
	 * Describes the order that a {@link Function} should appear in a sorted array (used in {@link #compareTo(Function)})
	 */
	public static final Class<?>[] sortOrder = {Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class};

	/**
	 * Returns a String representation of the Function
	 * @return String representation of function
	 */
	public abstract String toString();

	public abstract Function clone();

	/**
	 * Simplifies a {@link Function} multiple times
	 * @param times the amount of times it is simplified
	 * @return the simplified {@link Function}
	 */
	public Function simplifyTimes(int times) {
		Function newFunction, curFunction = this;
		for (int i = 0; i < times; i++) {
			newFunction = curFunction.simplify();
			if (newFunction.toString().equals(curFunction.toString()))
				return newFunction;
			curFunction = newFunction;
		}
		return curFunction;
	}

	/**
	 * Returns the derivative of the function, simplified
	 * @param varID the ID of the variable being differentiated (see {@link Parser#getVarID(char)}).
	 * @return the derivative of the {@link Function} it is called on, simplified
	 */
	public Function getSimplifiedDerivative(int varID) {
		if (Settings.cacheDerivatives && derivatives.containsKey(varID))
			return derivatives.get(varID);
		Function derivative = getDerivative(varID).simplify();
		if (Settings.cacheDerivatives)
			derivatives.put(varID, derivative);
		return derivative;
	}

	/**
	 * Substitutes a new {@link Function} into a variable
	 * @param varID the variable to be substituted into
	 * @param toReplace the {@link Function} that will be substituted
	 * @return the new {@link Function} after all substitutions are preformed
	 */
	public abstract Function substitute(int varID, Function toReplace);


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
	 * Two different Function types are sorted according to {@link #sortOrder} and {@link ArrLib#findClassValue(Function)}, and same types are sorted using {@link #compareSelf(Function)}
	 * @param that the {@link Function} compared to
	 * @return comparison
	 */
	public int compareTo(@NotNull Function that) {
		if (this.equals(that))
			return 0;
		else if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		else
			return (ArrLib.findClassValue(this) - ArrLib.findClassValue(that));
	}
}
