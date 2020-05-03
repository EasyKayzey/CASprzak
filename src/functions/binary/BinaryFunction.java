package functions.binary;

import functions.GeneralFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class BinaryFunction extends GeneralFunction {
	/**
	 * The first {@link GeneralFunction} in the binary operation
	 */
	protected final GeneralFunction function1;
	/**
	 * The second {@link GeneralFunction} in the binary operation
	 */
	protected final GeneralFunction function2;

	/**
	 * Constructs a new BinaryFunction
	 * @param function1 The first {@link GeneralFunction} in the binary operation
	 * @param function2 The second {@link GeneralFunction} in the binary operation
	 */
	public BinaryFunction(GeneralFunction function1, GeneralFunction function2) {
		this.function1 = function1;
		this.function2 = function2;
	}


	/**
	 * Returns the {@link GeneralFunction} stored in {@link #function1}
	 * @return the {@link GeneralFunction} stored in {@link #function1}
	 */
	public GeneralFunction getFunction1() {
		return function1;
	}

	/**
	 * Returns the {@link GeneralFunction} stored in {@link #function2}
	 * @return the {@link GeneralFunction} stored in {@link #function2}
	 */
	public GeneralFunction getFunction2() {
		return function2;
	}

	/**
	 * Returns an instance of this {@link GeneralFunction}, using the correct subclass
	 * @param function1 Constructor parameter 1
	 * @param function2 Constructor parameter 2
	 * @return an instance of this GeneralFunction
	 */
	public abstract BinaryFunction me(GeneralFunction function1, GeneralFunction function2);

	/**
	 * Substitutes all {@link functions.special.Variable} in the function with a specified {@link GeneralFunction}
	 * @param varID     the variable to be substituted into
	 * @param toReplace the {@link GeneralFunction} that will be substituted
	 * @return the function after the given substitution was made
	 */
	public GeneralFunction substitute(char varID, GeneralFunction toReplace) {
		return me(function1.substitute(varID, toReplace), function2.substitute(varID, toReplace));
	}


	public boolean equalsFunction(GeneralFunction that) {
		return this.getClass().equals(that.getClass()) && this.function1.equalsFunction(((BinaryFunction) that).function1) && this.function2.equalsFunction(((BinaryFunction) that).function2);
	}

	public int compareSelf(GeneralFunction that) {
		if (that instanceof BinaryFunction binaryFunction) {
			if (!this.function1.equalsFunction(binaryFunction.function1))
				return this.function1.compareTo(binaryFunction.function1);
			if (!this.function2.equalsFunction(binaryFunction.function2))
				return this.function2.compareTo(binaryFunction.function2);
		} else {
			throw new IllegalCallerException("Illegally called BinaryFunction.compareSelf on a non-BinaryFunction");
		}
		System.out.println("This is never supposed to happen, binaryFunction compareSelf");
		return 0;
	}


	public @NotNull Iterator<GeneralFunction> iterator() {
		return new BinaryIterator();
	}

	private class BinaryIterator implements Iterator<GeneralFunction> {
		private int loc;

		private BinaryIterator() {
			loc = 0;
		}

		@Override
		public boolean hasNext() {
			return loc < 2;
		}

		@SuppressWarnings("ValueOfIncrementOrDecrementUsed")
		@Override
		public GeneralFunction next() {
			if (!hasNext())
				throw new NoSuchElementException("Out of elements in BinaryFunction " + function2 + ", " + function1);
			return switch(loc++) {
				case 0 -> function2;
				case 1 -> function1;
				default -> throw new IllegalStateException("This code should never run in BinaryIterator");
			};
		}
	}
}
