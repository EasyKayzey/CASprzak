package tools;

import functions.Function;
import functions.commutative.CommutativeFunction;
import tools.helperclasses.FunctionPredicate;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SearchTools {

	/**
	 * Returns true if a {@link Function} in the function tree satisfies the predicate test
	 * @param input the Function to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean exists(Function input, FunctionPredicate test) {
		return existsExcluding(input, test, (a -> false));
	}

	/**
	 * Returns true if a {@link  Function} in the function tree satisfies the predicate test, excluding all Functions on branches satisfying the predicate exclude
	 * @param input the Function to be searched in
	 * @param test the predicate to be tested
	 * @param exclude the predicate to exclude
	 * @return true if found
	 */
	public static boolean existsExcluding(Function input, FunctionPredicate test, FunctionPredicate exclude) {
		if (exclude.test(input))
			return false;
		else if (test.test(input))
			return true;
		else
			for (Function f : input)
				if (existsExcluding(f, test, exclude))
					return true;
		return false;
	}


	/**
	 * Returns true if a direct child of this {@link Function} satisfies the predicate test
	 * @param input the function to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean existsSurface(Function input, FunctionPredicate test) {
		return existsSurfaceExcluding(input, test, (a -> false));
	}

	/**
	 * Returns true if a direct child of this {@link  Function} satisfies the predicate test, excluding all Functions satisfying the predicate exclude
	 * @param input the Function to be searched in
	 * @param test the predicate to be tested
	 * @param exclude the predicate to exclude
	 * @return true if found
	 */
	public static boolean existsSurfaceExcluding(Function input, FunctionPredicate test, FunctionPredicate exclude) {
		if (exclude.test(input))
			return false;
		else
			for (Function f : input)
				if (test.test(f))
					return true;
		return false;
	}


	/**
	 * Checks if this CommutativeFunction has a subset (as a Multiply) satisfying the condition, including empty and single-element products
	 * @param test the condition to be satisfied
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInSurfaceSubset(CommutativeFunction input, FunctionPredicate test) {
		return getSubsetIDs(input, test).size() > 0;
	}

	/**
	 * Checks if this CommutativeFunction has a subset not including excludeFromSubset (as a Multiply) satisfying the condition, including empty and single-element products
	 * @param test the condition to be satisfied
	 * @param excludeFromSubset subset that should be excluded
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInOppositeSurfaceSubset(CommutativeFunction input, FunctionPredicate test, FunctionPredicate excludeFromSubset) {
		return existsInOppositeSurfaceSubsetExcluding(input, test, excludeFromSubset, (f -> false));
	}

	/**
	 * Checks if this CommutativeFunction has a subset not including excludeFromSubset (as a Multiply) satisfying the condition, including empty and single-element products, excluding any branches that satisfy excludeFromSearch
	 * @param test the condition to be satisfied
	 * @param excludeFromSubset subset that should be excluded
	 * @param excludeFromSearch predicate to be excluded from the search
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInOppositeSurfaceSubsetExcluding(CommutativeFunction input, FunctionPredicate test, FunctionPredicate excludeFromSubset, FunctionPredicate excludeFromSearch) {
		Function[] functions = input.getFunctions();
		List<Integer> excludedIDs = getSubsetIDs(input, excludeFromSubset);
		for (int run : excludedIDs) {
			List<Function> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((~run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (existsExcluding(input.me(subset.toArray(new Function[0])), test, excludeFromSearch))
				return true;
		}
		return false;
	}

	private static List<Integer> getSubsetIDs(CommutativeFunction input, FunctionPredicate test) {
		Function[] functions = input.getFunctions();
		List<Integer> ids = new ArrayList<>();
		for (int run = 0; run < Math.pow(2, functions.length); run++) {
			List<Function> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (test.test(input.me(subset.toArray(new Function[0]))))
				ids.add(run);
		}
		return ids;
	}
}
