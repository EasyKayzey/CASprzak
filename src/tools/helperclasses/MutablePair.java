package tools.helperclasses;

public class MutablePair<T, U> extends AbstractMutablePair<T, U> {
	public T first;
	public U second;

	public MutablePair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public MutablePair(AbstractPair<T, U> pair) {
		this.first = pair.getFirst();
		this.second = pair.getSecond();
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
