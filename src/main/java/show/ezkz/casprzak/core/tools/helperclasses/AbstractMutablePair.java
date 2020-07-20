package show.ezkz.casprzak.core.tools.helperclasses;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractMutablePair<T, U> extends AbstractPair<T, U> {

	/**
	 * Sets the first item in the pair and returns the old value
	 * @param first the future value of the first item
	 * @return the old value of the first item
	 */
	public abstract T setFirst(T first);

	/**
	 * Sets the second item in the pair and returns the old value
	 * @param second the future value of the second item
	 * @return the old value of the second item
	 */
	public abstract U setSecond(U second);
}
