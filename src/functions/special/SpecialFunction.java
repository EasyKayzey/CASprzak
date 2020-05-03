package functions.special;

import functions.GeneralFunction;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class SpecialFunction extends GeneralFunction {
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
			throw new NoSuchElementException("No elements in a SpecialFunction");
		}
	}
}
