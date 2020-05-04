package functions;

public interface Invertible {

	/**
	 * Returns the Class corresponding to the inverse of this trig function
	 * @return the inverse class
	 */
	Class<? extends Invertible> getInverse();
}
