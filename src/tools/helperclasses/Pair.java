package tools.helperclasses;

import java.util.Map;

public class Pair<T, U> extends AbstractPair<T, U> {
	public final T first;
	public final U second;

	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public Pair(AbstractPair<T, U> pair) {
		this.first = pair.getFirst();
		this.second = pair.getSecond();
	}

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
