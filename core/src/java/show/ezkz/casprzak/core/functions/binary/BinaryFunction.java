package show.ezkz.casprzak.core.functions.binary;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import org.jetbrains.annotations.NotNull;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputBinary;
import show.ezkz.casprzak.core.functions.endpoint.Variable;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The abstract {@link BinaryFunction} class represents function of two inputs and that aren't commutable. Ex: {@code log_{2}(x)} or {@code 3^(x)}
 */
public abstract class BinaryFunction extends GeneralFunction {
	/**
	 * One {@link GeneralFunction} in the binary operation
	 */
	protected final GeneralFunction function1;
	/**
	 * The other {@link GeneralFunction} in the binary operation
	 */
	protected final GeneralFunction function2;

	/**
	 * Constructs a new {@link BinaryFunction}
	 * @param function1 The first {@link GeneralFunction} in the binary operation
	 * @param function2 The second {@link GeneralFunction} in the binary operation
	 */
	public BinaryFunction(GeneralFunction function1, GeneralFunction function2) {
		this.function1 = function1;
		this.function2 = function2;
	}


	/**
	 * Returns {@code simplify()}{@link #function1}
	 * @return {@code simplify()}{@link #function1}
	 */
	public GeneralFunction getFunction1() {
		return function1;
	}

	/**
	 * Returns {@code simplify()}{@link #function2}
	 * @return {@code simplify()}{@link #function2}
	 */
	public GeneralFunction getFunction2() {
		return function2;
	}

	/**
	 * Returns an instance of this {@link GeneralFunction}, using the correct subclass
	 * @param function1 Constructor parameter 1
	 * @param function2 Constructor parameter 2
	 * @return an instance of this {@link GeneralFunction}
	 */
	public abstract BinaryFunction getInstance(GeneralFunction function1, GeneralFunction function2);

	/**
	 * Substitutes all {@link Variable} in the function with a specified {@link GeneralFunction}
	 * @param test     the variable to be substituted into
	 * @param replacer the {@link GeneralFunction} that will be substituted
	 * @return the function after the given substitution was made
	 */
	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);
		else
			return getInstance(function1.substituteAll(test, replacer), function2.substituteAll(test, replacer));
	}


	public boolean equalsFunction(GeneralFunction that) {
		return this.getClass().equals(that.getClass()) && this.function1.equalsFunction(((BinaryFunction) that).function1) && this.function2.equalsFunction(((BinaryFunction) that).function2);
	}

	public int compareSelf(GeneralFunction that) {
		if (that instanceof BinaryFunction binaryFunction)
			if (!this.function1.equalsFunction(binaryFunction.function1))
				return this.function1.compareTo(binaryFunction.function1);
			else if (!this.function2.equalsFunction(binaryFunction.function2))
				return this.function2.compareTo(binaryFunction.function2);
			else
				throw new IllegalStateException("Called compareSelf on two equal functions: " + this + ", " + that + "");
		else
			throw new IllegalCallerException("Illegally called BinaryFunction.compareSelf on a non-BinaryFunction.");
	}

	public OutputFunction toOutputFunction() {
		return new OutputBinary(getClass().getSimpleName().toLowerCase(), function2.toOutputFunction(), function1.toOutputFunction());
	}

	/**
	 * Returns a new {@link Constant} of the {@link BinaryFunction} evaluated if both operands are a {@link Constant}
	 * @return a new {@link Constant} of the {@link BinaryFunction} evaluated if both operands are a {@link Constant}
	 */
	public GeneralFunction simplifyFOC() {
		if (function1 instanceof Constant && function2 instanceof Constant)
			return new Constant(this.evaluate()).simplify();
		else
			return this;
	}

	public int hashCode() {
		int code = this.getClass().hashCode();
		code = code * 31 + function1.hashCode();
		code = code * 31 + function2.hashCode();
		return code;
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
			return switch(loc++) {
				case 0 -> function2;
				case 1 -> function1;
				default -> throw new NoSuchElementException("Out of elements in BinaryFunction " + function2 + ", " + function1 + "");
			};
		}
	}
}
