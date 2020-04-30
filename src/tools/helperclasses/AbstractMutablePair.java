package tools.helperclasses;

public abstract class AbstractMutablePair<T, U> extends AbstractPair<T, U> {
	public abstract void setFirst(T first);
	public abstract void setSecond(U second);
}
