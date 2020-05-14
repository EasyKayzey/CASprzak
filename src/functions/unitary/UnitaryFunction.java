package functions.unitary;

import config.Settings;
import functions.GeneralFunction;
import functions.Invertible;
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

	@SuppressWarnings("ChainOfInstanceofChecks")
	public GeneralFunction simplify() {
		GeneralFunction newFunction = this.simplifyInternal();
		if (newFunction instanceof UnitaryFunction unit)
			newFunction = unit.simplifyInverse();

		if (newFunction instanceof UnitaryFunction unit)
			newFunction = unit.simplifyFOC();
		return newFunction;
	}

	/**
	 * Returns a new {@link Constant} of the {@link UnitaryFunction} evaluated if the {@link UnitaryFunction#operand} is a {@link Constant}
	 * @return a new {@link Constant} of the {@link UnitaryFunction} evaluated if the {@link UnitaryFunction#operand} is a {@link Constant}
	 */
	public GeneralFunction simplifyFOC() {
		if (Settings.simplifyFunctionsOfConstants && operand instanceof Constant)
			return new Constant(evaluate(null));
		else
			return this;
	}

	/**
	 * Returns a {@link GeneralFunction} of the {@link UnitaryFunction#operand} of the current {@link UnitaryFunction#operand} if it is an instance of the {@link Invertible#getInverse()} of the {@link UnitaryFunction}
	 * @return a {@link GeneralFunction} of the {@link UnitaryFunction#operand} of the current {@link UnitaryFunction#operand} if it is an instance of the {@link Invertible#getInverse()} of the {@link UnitaryFunction}
	 */
	public GeneralFunction simplifyInverse() {
		if (this instanceof Invertible inv && operand.getClass().isAssignableFrom(inv.getInverse()))
			return ((UnitaryFunction) operand).operand;
		else
			return this;
	}

	/**
	 * Returns an instance of this {@link GeneralFunction}
	 * @param operand Constructor parameter
	 * @return an instance of this {@link GeneralFunction}
	 */
	public abstract UnitaryFunction me(GeneralFunction operand);

	public UnitaryFunction clone() {
		return me(operand.clone());
	}

	/**
	 * Returns a copy of this {@link UnitaryFunction} with the {@link #operand} simplified using {@link GeneralFunction#simplify()}
	 * @return a copy of this {@link UnitaryFunction} with the {@link #operand} simplified using {@link GeneralFunction#simplify()}
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


	/**
	 * Returns a {@link UnitaryFunction} of the given type and of the given operand
	 * @param type The {@code Class} of the {@link UnitaryFunction} being returned
	 * @param operand The {@link UnitaryFunction#operand} of the {@link UnitaryFunction}
	 * @return a {@link UnitaryFunction} of the given type and of the given operand.
	 */
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
