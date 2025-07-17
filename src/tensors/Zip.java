package tensors;

import java.util.Iterator;
import java.util.function.BiFunction;

public class Zip<A, B, T> implements Iterator<T> {

	protected Iterator<A> first;
	protected Iterator<B> second;
	protected BiFunction<A, B, T> combiner;

	public Zip(Iterator<A> first, Iterator<B> second, BiFunction<A, B, T> combiner) {
		this.first = first;
		this.second = second;
		this.combiner = combiner;
	}


	@Override
	public boolean hasNext() {
		if (first.hasNext() ^ second.hasNext())
			throw new IllegalStateException("Iterators do not have equal size in zip.");
		return first.hasNext();
	}

	@Override
	public T next() {
		return combiner.apply(first.next(), second.next());
	}

	public <R> R fullReduce(R identity, BiFunction<R, T, R> combiner) {
		R current = identity;
		while (hasNext())
			current = combiner.apply(current, next());
		return current;
	}
}
