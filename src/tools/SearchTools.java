package tools;

import functions.Function;

import java.util.function.Predicate;

public class SearchTools {
	public static boolean exists(Function input, Predicate<? super Function> test) {
		return existsExcluding(input, test, (a -> false));
	}

	public static boolean existsExcluding(Function input, Predicate<? super Function> test, Predicate<? super Function> exclude) {
		for (Function f : input)
			if (!exclude.test(f) && (test.test(f) || existsExcluding(f, test, exclude)))
				return true;
		return false;
	}
}
