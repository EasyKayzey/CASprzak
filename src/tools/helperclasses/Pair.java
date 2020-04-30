package tools.helperclasses;

public class Pair<T, U> implements AbstractPair<T, U>{
	public final T first;
	public final U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public T getFirst() {
		return null;
	}

	@Override
	public U getSecond() {
		return null;
	}
}
