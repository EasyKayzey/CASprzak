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
		Function[] functions = input.getFunctions();
		for (int run = 0; run < Math.pow(2, functions.length); run++) {
			List<Function> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (test.test(input.me(subset.toArray(new Function[0]))))
				return true;
		}
		return false;
	}
}
