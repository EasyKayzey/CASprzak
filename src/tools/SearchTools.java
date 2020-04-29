package tools;

import functions.Function;

import java.util.function.Predicate;

public class SearchTools {
	public static boolean exists(Function input, Predicate<? super Function> test) {
		return existsExcluding(input, test, (a -> false));
	}

	public static boolean existsExcluding(Function input, Predicate<? super Function> test, Predicate<? super Function> exclude) {
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
}
