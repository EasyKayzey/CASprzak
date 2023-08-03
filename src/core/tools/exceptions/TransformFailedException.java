package core.tools.exceptions;

public class TransformFailedException extends Exception {
	/**
	 * To be thrown when the transform execution has failed
	 * @param message more information about the failed transform
	 */
	public TransformFailedException(String message) {
		super(message);
	}
}
