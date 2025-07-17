package tensors;

import java.util.List;
import java.util.function.UnaryOperator;

public interface Nested<I extends Nested<I, T>, T> extends Indexable<T> {

	int getRank();

	boolean matches(Nested<I, T> other);

	default boolean deepMatches(Nested<I, T> other) {
		return matches(other) && new Zip<>( // TODO test this with opposite upper/lower
				getElements().iterator(),
				other.getElements().iterator(),
				Nested::deepMatches
		).fullReduce(true, (t, r) -> t && r);
	}

	List<I> getElements();

	Nested<I, T> modifyWith(UnaryOperator<I> elementModifier,
							UnaryOperator<T> endpointModifier);

	default Nested<I, T> modifyWith(UnaryOperator<T> endpointModifier) {
		return modifyWith(i -> i, endpointModifier);
	}

	String toString();


}
