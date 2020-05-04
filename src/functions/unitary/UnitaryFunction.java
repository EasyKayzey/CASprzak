package functions.unitary;

import config.Settings;
import functions.GeneralFunction;
import functions.special.Constant;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;


public abstract class UnitaryFunction extends GeneralFunction {
	/**
	 * The {@link GeneralFunction} which the {@link UnitaryFunction} operates on
	 */
	public final GeneralFunction operand;

	/**
	 * Constructs a new UnitaryFunction
	 * @param operand The {@link GeneralFunction} which will be operated on
	 */
	public UnitaryFunction(GeneralFunction operand) {
		this.operand = operand;
	}


	public String toString() {
		return this.getClass().getSimpleName().toLowerCase() + "(" + operand.toString() + ")";
	}

	public GeneralFunction simplify() {
		UnitaryFunction newFunction = this.simplifyInternal();
		if (Settings.simplifyFunctionsOfConstants && newFunction.operand instanceof Constant)
			return new Constant(newFunction.evaluate(null));
		return newFunction;
	}

	/**
	 * Returns an instance of this {@link GeneralFunction}
	 * @param operand Constructor parameter
	 * @return an instance of this GeneralFunction
	 */
	public abstract UnitaryFunction me(GeneralFunction operand);

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

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);
		else
			return me(operand.substituteAll(test, replacer));
	}

	public boolean equalsFunction(GeneralFunction that) {
		return this.getClass().equals(that.getClass()) && this.operand.equalsFunction(((UnitaryFunction) that).operand);
	}

	public int compareSelf(GeneralFunction that) {
		return (this.operand.compareTo(((UnitaryFunction) that).operand));
	}


	public static UnitaryFunction newInstanceOf(Class<?> type, GeneralFunction operand) {
		try {
			Constructor<?>[] constructors = type.getConstructors();
			for (Constructor<?> constructor : constructors)
				if (constructor.getParameterCount() == 1)
					return (UnitaryFunction) constructor.newInstance(operand);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public @NotNull Iterator<GeneralFunction> iterator() {
		return new UnitaryIterator();
	}

	private class UnitaryIterator implements Iterator<GeneralFunction> {
		private boolean used;

		private UnitaryIterator() {
			used = false;
		}

		@Override
		public boolean hasNext() {
			return !used;
		}

		@Override
		public GeneralFunction next() {
			if (used)
				throw new NoSuchElementException("Out of elements in UnitaryFunction");
			used = true;
			return operand;
		}
	}
}
