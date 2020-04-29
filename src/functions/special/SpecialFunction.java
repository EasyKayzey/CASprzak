package functions.special;

import functions.Function;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class SpecialFunction extends Function {
	public @NotNull Iterator<Function> iterator() {
		return new SpecialIterator();
	}

	private static class SpecialIterator implements Iterator<Function> {
		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public Function next() {
			throw new NoSuchElementException("No elements in a SpecialFunction");
		}
	}
}
