package show.ezkz.casprzak.core.tools;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.CommutativeFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The {@link SearchTools} class provides a set of methods that perform functions related to searching through with the contents in a function tree and performing actions on those functions.
 */
public class SearchTools {

	private SearchTools(){}

	/**
	 * Returns true if any {@link GeneralFunction} in the function tree satisfies {@code test}
	 * @param input the {@link GeneralFunction} to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean existsAny(GeneralFunction input, Predicate<? super GeneralFunction> test) {
		return existsExcluding(input, test, a -> false);
	}

	/**
	 * Returns true if a {@link  GeneralFunction} in the function tree satisfies {@code test}, excluding all functions on branches whose parent satisfies {@code exclude}
	 * @param input the {@link GeneralFunction} to be searched in
	 * @param test the predicate to be tested
	 * @param exclude the predicate to be excluded
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
	 * Returns true if at least one direct child of this {@link GeneralFunction} satisfies {@code test}
	 * @param input the function to be searched in
	 * @param test the predicate to be tested
	 * @return true if found
	 */
	public static boolean existsSurface(GeneralFunction input, Predicate<? super GeneralFunction> test) {
		for (GeneralFunction f : input)
			if (test.test(f))
				return true;
		return false;
	}


	/**
	 * Checks if this {@link CommutativeFunction} has a subset (as a new instance of its class) satisfying {@code test}, including empty and single-element functions
	 * @param input the {@link CommutativeFunction} to be searched in
	 * @param test the condition to be satisfied
	 * @return true if the condition was satisfied by a subset
	 */
	public static boolean existsInSurfaceSubset(CommutativeFunction input, Predicate<? super GeneralFunction> test) {
		GeneralFunction[] functions = input.getFunctions();
		for (int run = 0; run < Math.pow(2, functions.length); run++) {
			List<GeneralFunction> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			if (test.test(input.getInstance(subset.toArray(new GeneralFunction[0]))))
				return true;
		}
		return false;
	}

	/**
	 * Checks if this {@link CommutativeFunction} has a subset (as a new instance of its class), including empty and single-element functions, that:
	 * - Is the complement of a subset satisfying {@code excludeFromSubset}
	 * - Satisfies {@code test}
	 * @param input the {@link CommutativeFunction} to be searched in
	 * @param test the condition to be satisfied
	 * @param excludeFromSubset the predicate for the subset that should be excluded
	 * @return true if the conditions above were satisfied by a subset of {@code input}
	 */
	public static boolean existsInOppositeSurfaceSubset(CommutativeFunction input, Predicate<? super GeneralFunction> test, Predicate<? super GeneralFunction> excludeFromSubset) {
		GeneralFunction[] functions = input.getFunctions();
		List<Integer> excludedIDs = getSubsetIDs(input, excludeFromSubset);
		for (int run : excludedIDs) {
			List<GeneralFunction> subset = new ArrayList<>();
			for (int ix = 0; ix < functions.length; ix++)
				if (((~run >> ix) & 1) > 0)
					subset.add(functions[ix]);
			CommutativeFunction thisFunction = input.getInstance(subset.toArray(new GeneralFunction[0]));
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
			if (test.test(input.getInstance(subset.toArray(new GeneralFunction[0]))))
				ids.add(run);
		}
		return ids;
	}

	/**
	 * Applies the specified consumer recursively down the function tree for every node satisfying the test
	 * @param input the {@link GeneralFunction} to be recursively consumed
	 * @param consumer the consumer
	 * @param test the test to be satisfied
	 */
	public static void consumeIf(GeneralFunction input, Consumer<? super GeneralFunction> consumer, Predicate<? super GeneralFunction> test) {
		if (test.test(input))
			consumer.accept(input);
		for (GeneralFunction f : input)
			consumeIf(f, consumer, test);
	}


}
