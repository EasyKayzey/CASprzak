package core.tools.helperclasses;

import java.util.Map;

public class Pair<T, U> extends AbstractPair<T, U> {
	private final T first;
	private final U second;

	/**
	 * Creates a pair by specifying its values
	 * @param first first value in the pair
	 * @param second second value in the pair
	 */
	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Creates a pair using another pair
	 * @param pair the pair containing the values to be used
	 */
	public Pair(AbstractPair<T, U> pair) {
		this.first = pair.getFirst();
		this.second = pair.getSecond();
	}

	/**
	 * Creates a pair using a map entry
	 * @param entry the entry containing the values to be used
	 */
	public Pair(Map.Entry<T, U> entry) {
		this.first = entry.getKey();
		this.second = entry.getValue();
	}

	@Override
	public T getFirst() {
		return first;
	}

	@Override
	public U getSecond() {
		return second;
	}
}
