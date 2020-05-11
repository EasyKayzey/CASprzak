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

	/**
	 * Returns the toString of the pair in the form {@code <first, second>}
	 * @return the toString as specified
	 */
	public String toString() {
		return "<" + getFirst() + ", " + getSecond() + ">";
	}
}
