package show.ezkz.casprzak.core.functions.unitary;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.Invertible;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import org.jetbrains.annotations.NotNull;
import show.ezkz.casprzak.core.output.OutputFunction;
import show.ezkz.casprzak.core.output.OutputUnitary;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The abstract {@link UnitaryFunction} class represents function of one input. Ex: {@code cos} or {@code abs}
 */
public abstract class UnitaryFunction extends GeneralFunction {
	/**
	 * The {@link GeneralFunction} which the {@link UnitaryFunction} operates on
	 */
	public final GeneralFunction operand;

	/**
	 * Constructs a new {@link UnitaryFunction}
	 * @param operand The {@link GeneralFunction} which will be operated on
	 */
	public UnitaryFunction(GeneralFunction operand) {
		this.operand = operand;
	}


	public String toString() {
		return this.getClass().getSimpleName().toLowerCase() + "(" + operand.toString() + ")";
	}

	public GeneralFunction simplify() {
		GeneralFunction newFunction = this.simplifyInternal();
		if (newFunction instanceof UnitaryFunction unit)
			newFunction = unit.simplifyInverse();

		if (newFunction instanceof UnitaryFunction unit)
			newFunction = unit.simplifyFOC();
		return newFunction;
	}

	/**
	 * Returns a new {@link Constant} of the {@link UnitaryFunction} evaluated if the {@link #operand} is a {@link Constant}
	 * @return a new {@link Constant} of the {@link UnitaryFunction} evaluated if the {@link #operand} is a {@link Constant}
	 */
	public GeneralFunction simplifyFOC() {
		if (Settings.simplifyFunctionsOfConstants && operand instanceof Constant)
			return new Constant(evaluate(Map.of()));
		else
			return this;
	}

	/**
	 * If {@link #operand} is an instance of the inverse of this function, returns {@code operand.operand}
	 * @return {@code operand.operand} if {@link #operand} is an instance of the inverse, and {@code this} otherwise
	 */
	public GeneralFunction simplifyInverse() {
		if (this instanceof Invertible inv && operand.getClass().isAssignableFrom(inv.getInverse()))
			return ((UnitaryFunction) operand).operand;
		else
			return this;
	}

	/**
	 * Returns a new instance of this {@link UnitaryFunction}
	 * @param operand the {@link GeneralFunction} to be operated on
	 * @return a new instance of this {@link UnitaryFunction}
	 */
	public abstract UnitaryFunction getInstance(GeneralFunction operand);

	public UnitaryFunction clone() {
		return getInstance(operand.clone());
	}

	/**
	 * Simplifies the {@link #operand} using {@link GeneralFunction#simplify()}
	 * @return an instance of this {@link UnitaryFunction} with the {@link #operand} simplified
	 */
	public UnitaryFunction simplifyInternal() {
		return getInstance(operand.simplify());
	}

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);
		else
			return getInstance(operand.substituteAll(test, replacer));
	}

	public boolean equalsFunction(GeneralFunction that) {
		return this.getClass().equals(that.getClass()) && this.operand.equalsFunction(((UnitaryFunction) that).operand);
	}

	public int compareSelf(GeneralFunction that) {
		return (this.operand.compareTo(((UnitaryFunction) that).operand));
	}


	public OutputFunction toOutputFunction() {
		return new OutputUnitary(getClass().getSimpleName().toLowerCase(),  operand.toOutputFunction());
	}

	/**
	 * Returns a {@link UnitaryFunction} of the given type and of the given operand
	 * @param type The {@code Class} of the {@link UnitaryFunction} being returned
	 * @param operand The {@link #operand} of the {@link UnitaryFunction}
	 * @return a new {@link UnitaryFunction} of the given type and of the given operand
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

	public int hashCode() {
		int code = this.getClass().hashCode();
		code = code * 31 + operand.hashCode();
		return code;
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
				throw new NoSuchElementException("Out of elements in UnitaryFunction.");
			used = true;
			return operand;
		}
	}
}
