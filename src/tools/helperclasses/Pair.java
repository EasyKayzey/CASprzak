package tools.helperclasses;

public class Pair<T, U> extends AbstractPair<T, U> {
	public final T first;
	public final U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
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
