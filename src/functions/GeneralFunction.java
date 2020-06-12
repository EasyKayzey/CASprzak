package functions;

import config.Settings;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.binary.integer.IntegerBinaryFunction;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.commutative.integer.IntegerCommutativeFunction;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.integer.IntegerUnitaryFunction;
import functions.unitary.piecewise.PiecewiseFunction;
import functions.unitary.specialcases.SpecialCaseBinaryFunction;
import functions.unitary.transforms.Transformation;
import functions.unitary.trig.inverse.InverseTrigFunction;
import functions.unitary.trig.normal.TrigFunction;
import org.jetbrains.annotations.NotNull;
import tools.MiscTools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A {@link GeneralFunction} is the generalized abstract function used throughout the CAS.
 * It is critical to note that ALL FUNCTIONS ARE IMMUTABLE: as a consequence, methods such as {@link #simplify()} return a function that has been simplified rather than simplifying the caller in place.
 */
public abstract class GeneralFunction implements Evaluable, Differentiable, Simplifiable, Comparable<GeneralFunction>, Iterable<GeneralFunction> {

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
	 * Fixes some variables to the values given in the {@code Map<String, Double> values} by substituting in a {@link Constant} for each of those {@link Variable}s
	 * @param values the map defining the substitutions to be made
	 * @return a new function with the substitutions made
	 */
	public GeneralFunction setVariables(Map<String, Double> values) {
		//noinspection unchecked
		return substituteVariables(
				Map.ofEntries(values.entrySet().stream()
						.map(e -> Map.entry(e.getKey(), new Constant(e.getValue())))
						.toArray(Map.Entry[]::new)
				)
		);
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
	public int compareTo(@NotNull GeneralFunction that) {
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
	public abstract @NotNull Iterator<GeneralFunction> iterator();
}
