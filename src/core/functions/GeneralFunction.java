package core.functions;

import core.config.Settings;
import core.functions.binary.Logb;
import core.functions.binary.Pow;
import core.functions.binary.integer.IntegerBinaryFunction;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.commutative.integer.IntegerCommutativeFunction;
import core.functions.endpoint.Constant;
import core.functions.endpoint.Variable;
import core.functions.unitary.integer.IntegerUnitaryFunction;
import core.functions.unitary.piecewise.PiecewiseFunction;
import core.functions.unitary.specialcases.SpecialCaseBinaryFunction;
import core.functions.unitary.transforms.Transformation;
import core.functions.unitary.trig.inverse.InverseTrigFunction;
import core.functions.unitary.trig.normal.TrigFunction;
import core.tools.MiscTools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link GeneralFunction} is the generalized abstract function used throughout the CAS.
 * It is critical to note that ALL FUNCTIONS ARE IMMUTABLE: as a consequence, methods such as {@link #simplify()} return a function that has been simplified rather than simplifying the caller in place.
 */
public abstract class GeneralFunction implements Evaluable, Differentiable, Simplifiable, Comparable<GeneralFunction>, Iterable<GeneralFunction>, Outputable {

	/**
	 * Describes the order that a {@link GeneralFunction} should appear in a sorted array (used in {@link #compareTo(GeneralFunction)})
	 */
	@SuppressWarnings("ClassReferencesSubclass")
	public static final Class<?>[] sortOrder = {
			Constant.class, // Must always be first
			Variable.class,
			Product.class,
			Pow.class,
			Logb.class,
			PiecewiseFunction.class,
			IntegerCommutativeFunction.class,
			IntegerBinaryFunction.class,
			IntegerUnitaryFunction.class,
			SpecialCaseBinaryFunction.class,
			Transformation.class,
			InverseTrigFunction.class,
			TrigFunction.class,
			Sum.class,
	};

	/**
	 * Caches derivatives with the key corresponding to the {@code varID} of the derivative
	 */
	protected final Map<String, GeneralFunction> derivatives = new HashMap<>();

	/**
	 * Returns a String representation of this {@link GeneralFunction}
	 * @return String representation of this function
	 */
	public abstract String toString();

	/**
	 * Returns a clone of this {@link GeneralFunction}
	 * @return a clone of this function
	 */
	public abstract GeneralFunction clone();


	/**
	 * Overloads {@link Evaluable#evaluate(Map)} with no arguments, passing a {@code Collections#EMPTY_MAP} by default
	 * @return the function evaluated with no arguments
	 */
	@SuppressWarnings("unchecked")
	public double evaluate() {
		return evaluate(Collections.EMPTY_MAP);
	}


	public GeneralFunction getSimplifiedDerivative(String varID) {
		if (Settings.cacheDerivatives && derivatives.containsKey(varID))
			return derivatives.get(varID);
		GeneralFunction derivative = getDerivative(varID).simplify();
		if (Settings.cacheDerivatives)
			derivatives.put(varID, derivative);
		return derivative;
	}

	public GeneralFunction getNthDerivative(String varID, int N) {
		GeneralFunction currentFunction = this;
		for (int i = 0; i < N; i++)
			currentFunction = currentFunction.getSimplifiedDerivative(varID);

		return currentFunction;
	}

	/**
	 * Returns the value of the derivative at {@code point}
	 * @param varID the variable being differentiated against
	 * @param point the point to find the derivative at
	 * @return the value of the derivative at {@code point}
	 */
	@SuppressWarnings("unused")
	public double derivativeAt(String varID, Map<String, Double> point) {
		return getDerivative(varID).evaluate(point);
	}

	/**
	 * Replaces every {@link GeneralFunction} that satisfies the {@code test} using the action specified by {@code replacer}
	 * @param test checks if the function should be replaced
	 * @param replacer replaces the function
	 * @return a new {@link GeneralFunction} with all replacements made
	 */
	public abstract GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer);

	/**
	 * Substitutes variables with functions as specified in a map
	 * @param toSubstitute the map between {@link Variable} strings and {@link GeneralFunction}s
	 * @return the new {@link GeneralFunction} after all substitutions are preformed
	 */
	public GeneralFunction substituteVariables(Map<String, ? extends GeneralFunction> toSubstitute) {
		return substituteAll(f -> (f instanceof Variable v && toSubstitute.containsKey(v.varID)), f -> toSubstitute.get(((Variable) f).varID));
	}


	/**
	 * Returns true when the two fully-simplified functions are equal
	 * @param that The {@link GeneralFunction} that the current function is being checked against
	 * @return true if the two functions are equal
	 */
	public abstract boolean equalsFunction(GeneralFunction that);

	/**
	 * Simplifies the two functions, then compares them with {@link #equalsFunction(GeneralFunction)}
	 * @param that the function to be compared against
	 * @return true if they're equal when simplified
	 */
	public boolean equalsSimplified(GeneralFunction that) {
		return this.simplify().equalsFunction(that.simplify());
	}

	/**
	 * Compares two functions with {@link #equalsFunction(GeneralFunction)}
	 * @param that the object to be compared against
	 * @return true if they're equal
	 */
	public boolean equals(Object that) {
		if (!(that instanceof GeneralFunction))
			return false;
		return this.equalsFunction((GeneralFunction) that);
	}

	/**
	 * Used internally for comparing two functions of <b>the same exact type</b>
	 * @param that the {@link GeneralFunction} that this is compared against
	 * @return the comparison
	 */
	protected abstract int compareSelf(GeneralFunction that);

	/**
	 * {@link GeneralFunction}s of different types are sorted according to {@link #sortOrder} and {@link MiscTools#findClassValue(GeneralFunction)}, and functions of the same exact type are sorted using {@link #compareSelf(GeneralFunction)}
	 * @param that the {@link GeneralFunction} that this is compared against
	 * @return the comparison
	 */
	public int compareTo(GeneralFunction that) {
		if (this.equalsFunction(that))
			return 0;
		else if (this.getClass().equals(that.getClass()))
			return compareSelf(that);
		else {
			int classDelta = MiscTools.findClassValue(this) - MiscTools.findClassValue(that);
			if (classDelta != 0)
				return classDelta;
			else
				return this.getClass().getSimpleName().compareTo(that.getClass().getSimpleName());
		}
	}

	/**
	 * Returns a hash code value for this object
	 * @return a hash code value for this object
	 */
	public abstract int hashCode();

	/**
	 * Returns an iterator over the operands of this {@link GeneralFunction}
	 * @return an iterator over the operands of this {@link GeneralFunction}
	 */
	public abstract Iterator<GeneralFunction> iterator();
}
