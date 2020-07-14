package output;

public class ToStringManager {
	/**
	 * Adds parentheses to `function` only if it is an {@link OutputCommutative} or {@link OutputBinary}
	 * @param function the function to be maybe parenthesized
	 * @return the function, possibly parenthesized
	 */
	public static OutputFunction maybeParenthesize(OutputFunction function) {
		if (function instanceof OutputCommutative || function instanceof OutputBinary)
			return new OutputParenthesizer(function);
		else
			return function;
	}
}
