package functions;

/**
 * Any classes implementing {@link Invertible} have an inverse whose {@link Class} is returned by {@link #getInverse()}.
 */
public interface Invertible {

	/**
	 * Returns the {@link Class} corresponding to the inverse of this function
	 * @return the inverse {@link Class}
	 */
	Class<?> getInverse();
}
