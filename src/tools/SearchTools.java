package tools;

import functions.Function;
import tools.helperclasses.FunctionPredicate;

@SuppressWarnings("unused")
public class SearchTools {
	public static boolean exists(Function input, FunctionPredicate test) {
		return existsExcluding(input, test, (a -> false));
	}

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

	public static boolean existsSurface(Function input, FunctionPredicate test) {
		return existsSurfaceExcluding(input, test, (a -> false));
	}

	public static boolean existsSurfaceExcluding(Function input, FunctionPredicate test, FunctionPredicate exclude) {
		if (exclude.test(input))
			return false;
		else
			for (Function f : input)
				if (test.test(f))
					return true;
		return false;
	}
}
