package functions.endpoint;

import functions.GeneralFunction;
import org.jetbrains.annotations.NotNull;
import output.OutputFunction;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * All classes extending {@link EndpointFunction} are endpoints of the function tree as they have no input or operands.
 */
public abstract class EndpointFunction extends GeneralFunction implements OutputFunction {

	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		if (test.test(this))
			return replacer.apply(this);
		else
			return this;
	}

	public OutputFunction toOutputFunction() {
		return this;
	}

	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	public List<OutputFunction> getOperands() {
		return Collections.emptyList();
	}

	public String toLatex() {
		return toString();
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
			throw new NoSuchElementException("No elements in a EndpointFunction.");
		}
	}
}
