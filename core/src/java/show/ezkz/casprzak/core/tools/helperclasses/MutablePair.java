package show.ezkz.casprzak.core.tools.helperclasses;

import java.util.Map;

public class MutablePair<T, U> extends AbstractMutablePair<T, U> {
	private T first;
	private U second;

	/**
	 * Creates a mutable pair by specifying its values
	 * @param first first value in the pair
	 * @param second second value in the pair
	 */
	public MutablePair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Creates a mutable pair using another pair
	 * @param pair the pair containing the values to be used
	 */
	public MutablePair(AbstractPair<T, U> pair) {
		this.first = pair.getFirst();
		this.second = pair.getSecond();
	}

	/**
	 * Creates a mutable pair using a map entry
	 * @param entry the entry containing the values to be used
	 */
	public MutablePair(Map.Entry<T, U> entry) {
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

	@Override
	public T setFirst(T first) {
		T oldFirst = this.first;
		this.first = first;
		return oldFirst;
	}

	@Override
	public U setSecond(U second) {
		U oldSecond = this.second;
		this.second = second;
		return oldSecond;
	}
}
