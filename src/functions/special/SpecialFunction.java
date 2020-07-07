package functions.special;

import functions.GeneralFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * All classes extending {@link SpecialFunction} are endpoints of the function tree as they have no input or operands.
 */
public abstract class SpecialFunction extends GeneralFunction {

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);
		else
			return this;
	}

	public @NotNull Iterator<GeneralFunction> iterator() {
		return new SpecialIterator();
	}

	private static class SpecialIterator implements Iterator<GeneralFunction> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public GeneralFunction next() {
			throw new NoSuchElementException("No elements in a SpecialFunction.");
		}
	}
}
