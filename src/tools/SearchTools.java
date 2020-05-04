package tools;

import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import functions.special.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SearchTools {

	/**
	 * Returns a {@link Predicate<GeneralFunction>} describing whether a given GeneralFunction is an instance of {@link Variable} with varID equal to the specified variable
	 * @param varID the variable to be checked for
	 * @return the predicate described above
	 */
	public static Predicate<GeneralFunction> isVariable(char varID) {
		return input -> ((input instanceof Variable v) && (v.varID == varID));
	}

	/**
	 * Returns true if a {@link GeneralFunction} in the function tree satisfies the predicate test
	 * @param input the GeneralFunction to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean exists(GeneralFunction input, Predicate<? super GeneralFunction> test) {
		return existsExcluding(input, test, a -> false);
	}

	/**
	 * Returns true if a {@link  GeneralFunction} in the function tree satisfies the predicate test, excluding all Functions on branches satisfying the predicate exclude
	 * @param input the GeneralFunction to be searched in
	 * @param test the predicate to be tested
	 * @param exclude the predicate to exclude
	 * @return true if found
	 */
	public static boolean existsExcluding(GeneralFunction input, Predicate<? super GeneralFunction> test, Predicate<? super GeneralFunction> exclude) {
		if (exclude.test(input))
			return false;
		else if (test.test(input))
			return true;
		else
			for (GeneralFunction f : input)
				if (existsExcluding(f, test, exclude))
					return true;
		return false;
	}


	/**
	 * Returns true if a direct child of this {@link GeneralFunction} satisfies the predicate test
	 * @param input the function to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean existsSurface(GeneralFunction input, Predicate<? super GeneralFunction> test) {
		return existsSurfaceExcluding(input, test, (a -> false));
	}

	/**
	 * Returns true if a direct child of this {@link  GeneralFunction} satisfies the predicate test, excluding all Functions satisfying the predicate exclude
	 * @param input the GeneralFunction to be searched in
	 * @param test the predicate to be tested
	 * @param exclude the predicate to exclude
	 * @return true if found
	 */
	public static boolean existsSurfaceExcluding(GeneralFunction input, Predicate<? super GeneralFunction> test, Predicate<? super GeneralFunction> exclude) {
		if (exclude.test(input))
			return false;
		else
			for (GeneralFunction f : input)
				if (test.test(f))
					return true;
		return false;
	}


	/**
	 * Checks if this CommutativeFunction has a subset (as a Multiply) satisfying the condition, including empty and single-element products
	 * @param test the condition to be satisfied
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInSurfaceSubset(CommutativeFunction input, Predicate<? super GeneralFunction> test) {
		return getSubsetIDs(input, test).size() > 0;
	}

	/**
	 * Checks if this CommutativeFunction has a subset not including excludeFromSubset (as a Multiply) satisfying the condition, including empty and single-element products
	 * @param test the condition to be satisfied
	 * @param excludeFromSubset subset that should be excluded
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInOppositeSurfaceSubset(CommutativeFunction input, Predicate<? super GeneralFunction> test, Predicate<? super GeneralFunction> excludeFromSubset) {
		GeneralFunction[] functions = input.getFunctions();
		List<Integer> excludedIDs = getSubsetIDs(input, excludeFromSubset);
		for (int run : excludedIDs) {
			List<GeneralFunction> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((~run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			CommutativeFunction thisFunction = input.me(subset.toArray(new GeneralFunction[0]));
			if (test.test(thisFunction))
				return true;
		}
		return false;
	}

	private static List<Integer> getSubsetIDs(CommutativeFunction input, Predicate<? super GeneralFunction> test) {
		GeneralFunction[] functions = input.getFunctions();
		List<Integer> ids = new ArrayList<>();
		for (int run = 0; run < Math.pow(2, functions.length); run++) {
			List<GeneralFunction> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (test.test(input.me(subset.toArray(new GeneralFunction[0]))))
				ids.add(run);
		}
		return ids;
	}

	public static void consumeIf(GeneralFunction input, Consumer<? super GeneralFunction> consumer, Predicate<? super GeneralFunction> test) {
		if (test.test(input))
			consumer.accept(input);
		for (GeneralFunction f : input)
			consumeIf(f, consumer, test);
	}

	public static void consumeAll(GeneralFunction input, Consumer<? super GeneralFunction> consumer) {
		consumeIf(input, consumer, f -> true);
	}

	public static Set<Character> getAllVariables(GeneralFunction input) {
		Set<Character> vars = new HashSet<>();
		consumeIf(input, f -> vars.add(((Variable) f).varID), f -> (f instanceof Variable));
		return vars;
	}
}
