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
	public final Function operand;

	/**
	 * Constructs a new UnitaryFunction
	 * @param operand The {@link Function} which will be operated on
	 */
	public UnitaryFunction(Function operand) {
		this.operand = operand;
	}


	public String toString() {
		return this.getClass().getSimpleName().toLowerCase() + "(" + operand.toString() + ")";
	}

	public Function simplify() {
		UnitaryFunction newFunction = this.simplifyInternal();
		if (Settings.simplifyFunctionsOfConstants && newFunction.operand instanceof Constant)
			return new Constant(newFunction.evaluate(null));
		return newFunction;
	}

	/**
	 * Returns an instance of this {@link Function}
	 * @param operand Constructor parameter
	 * @return an instance of this Function
	 */
	public abstract UnitaryFunction me(Function operand);

	public UnitaryFunction clone() {
		return me(operand.clone());
	}

	/**
	 * Returns the {@link UnitaryFunction} with {@link #operand}.{@link #simplify()}
	 * @return the {@link UnitaryFunction} with {@link #operand}.{@link #simplify()}
	 */
	public UnitaryFunction simplifyInternal() {
		return me(operand.simplify());
	}

	public UnitaryFunction substitute(char varID, Function toReplace) {
		return me(operand.substitute(varID, toReplace));
	}

	public boolean equalsFunction(Function that) {
		return this.getClass().equals(that.getClass()) && this.operand.equalsFunction(((UnitaryFunction) that).operand);
	}

	public int compareSelf(Function that) {
		return (this.operand.compareTo(((UnitaryFunction) that).operand));
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

		@Override
		public Function next() {
			if (used)
				throw new NoSuchElementException("Out of elements in UnitaryFunction");
			used = true;
			return operand;
		}
	}
}
