package CASprzak;

import CASprzak.SpecialFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.UnitaryFunctions.*;

import java.util.HashMap;

public abstract class Function implements Evaluable, Differentiable, Simplifiable, Substitutable, Comparable<Function> {

	/**
	 * Caches derivatives with the key corresponding to the varID of the derivative
	 */
	protected HashMap<Integer, Function> derivatives = new HashMap<>();

	/**
	 * Describes the order that a {@link Function} should appear in a sorted array (used in {@link #compareTo(Function)}
	 */
	protected static final Class<?>[] sortOrder = {Constant.class, Variable.class, Pow.class, Logb.class, Multiply.class, UnitaryFunction.class, Add.class};

	public abstract String toString();

	public abstract Function clone();

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
	 * @param varID the ID of the variable being differentiated (see {@link Parser#getVarID(char)}).
	 * @return the derivative of the {@link CASprzak.Function} it is called on, simplified.
	 */
	public Function getSimplifiedDerivative(int varID) {
		if (Settings.cacheDerivatives && derivatives.containsKey(varID))
			return derivatives.get(varID);
		Function derivative = getDerivative(varID).simplify();
		if (Settings.cacheDerivatives)
			derivatives.put(varID, derivative);
		return derivative;
	}

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
	public int compareTo(Function that) {
		if (this.equals(that))
			return 0;
		else if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		else
			return (ArrLib.findClassValue(this) - ArrLib.findClassValue(that));
	}
}
