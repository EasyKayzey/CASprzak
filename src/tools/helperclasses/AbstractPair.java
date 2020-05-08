package tools.helperclasses;

public abstract class AbstractPair<T, U> {

	/**
	 * Returns the value of the first item in the pair
	 * @return the value of the first item in the pair
	 */
	public abstract T getFirst();

	/**
	 * Returns the value of the second item in the pair
	 * @return the value of the second item in the pair
	 */
	public abstract U getSecond();

	public String toString() {
		return "<" + getFirst() + ", " + getSecond() + ">";
	}
}
