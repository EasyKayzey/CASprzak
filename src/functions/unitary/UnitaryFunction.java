package functions.unitary;

import config.Settings;
import functions.Function;
import functions.special.Constant;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;


public abstract class UnitaryFunction extends Function {
	/**
	 * The {@link Function} which the {@link UnitaryFunction} operates on
	 */
	protected final Function function;

	/**
	 * Constructs a new UnitaryFunction
	 * @param function The {@link Function} which will be operated on
	 */
	public UnitaryFunction(Function function) {
		this.function = function;
	}



	public String toString() {
		return this.getClass().getSimpleName().toLowerCase() + "(" + function.toString() + ")";
	}

	public Function simplify() {
		UnitaryFunction newFunction = this.simplifyInternal();
		if (Settings.simplifyFunctionsOfConstants && newFunction.function instanceof Constant)
			return new Constant(newFunction.evaluate(null));
		return newFunction;
	}

	/**
	 * Returns an instance of this {@link Function}
	 * @param function Constructor parameter
	 * @return an instance of this Function
	 */
	public abstract UnitaryFunction me(Function function);

	public UnitaryFunction clone() {
		return me(function.clone());
	}

	/**
	 * Returns the {@link UnitaryFunction} with {@link #function}.{@link #simplify()}
	 * @return the {@link UnitaryFunction} with {@link #function}.{@link #simplify()}
	 */
	public UnitaryFunction simplifyInternal() {
		return me(function.simplify());
	}

	public UnitaryFunction substitute(char varID, Function toReplace) {
		return me(function.substitute(varID, toReplace));
	}

	public boolean equals(Function that) {
		return this.getClass().equals(that.getClass()) && this.function.equals(((UnitaryFunction) that).function);
	}

	public int compareSelf(Function that) {
		return (this.function.compareTo(((UnitaryFunction) that).function));
	}


	public @NotNull Iterator<Function> iterator() {
		return new UnitaryIterator();
	}

	private class UnitaryIterator implements Iterator<Function> {
		private boolean used;

		private UnitaryIterator() {
			used = false;
		}

		@Override
		public boolean hasNext() {
			return !used;
		}

		@SuppressWarnings("ValueOfIncrementOrDecrementUsed")
		@Override
		public Function next() {
			if (used)
				throw new NoSuchElementException("Out of elements in UnitaryFunction");
			used = true;
			return function;
		}
	}
}
