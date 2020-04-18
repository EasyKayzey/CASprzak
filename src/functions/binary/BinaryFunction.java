package functions.binary;

import functions.Function;

public abstract class BinaryFunction extends Function {
	/**
	 * The first {@link Function} in the binary operation
	 */
	protected final Function function1;
	/**
	 * The second {@link Function} in the binary operation
	 */
	protected final Function function2;

	/**
	 * Constructs a new BinaryFunction
	 * @param function1 The first {@link Function} in the binary operation
	 * @param function2 The second {@link Function} in the binary operation
	 */
	public BinaryFunction(Function function1, Function function2) {
		this.function1 = function1;
		this.function2 = function2;
	}


	/**
	 * @return the {@link Function} stored in {@link #function1}
	 */
	public Function getFunction1() {
		return function1;
	}

	/**
	 * @return the {@link Function} stored in {@link #function2}
	 */
	public Function getFunction2() {
		return function2;
	}

	//TODO document all the "me"s
	public abstract BinaryFunction me(Function function1, Function function2);

	/**
	 * Substitutes all {@link functions.special.Variable} in the function with a specified {@link Function}
	 * @param varID     the variable to be substituted into
	 * @param toReplace the {@link Function} that will be substituted
	 * @return the function after the given substitution was made
	 */
	public Function substitute(int varID, Function toReplace) {
		return me(function1.substitute(varID, toReplace), function2.substitute(varID, toReplace));
	}


	/**
	 * Returns true when the specified {@link Function} equals the current {@link Function}. Two {@link BinaryFunction}s are equal if their stored instance functions are equal. Returns false otherwise.
	 * @param that The function that the current Function is being compared to
	 * @return true if the functions are equal
	 */
	public boolean equals(Function that) {
		return this.getClass().equals(that.getClass()) && this.function1.equals(((BinaryFunction) that).function1) && this.function2.equals(((BinaryFunction) that).function2);
	}

	public int compareSelf(Function that) {
		if (that instanceof BinaryFunction binaryFunction) {
			if (!this.function1.equals(binaryFunction.function1))
				return this.function1.compareTo(binaryFunction.function1);
			if (!this.function2.equals(binaryFunction.function2))
				return this.function2.compareTo(binaryFunction.function2);
		} else {
			throw new IllegalArgumentException("Illegally called BinaryFunction.compareSelf on a non-BinaryFunction");
		}
		System.out.println("This is never supposed to happen, binaryFunction compareSelf");
		return 0;
	}
}
