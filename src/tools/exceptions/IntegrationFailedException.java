package tools.exceptions;

public class IntegrationFailedException extends TransformFailedException {
	/**
	 * To be thrown when integration has failed
	 * @param message more information about the failed integration
	 */
	public IntegrationFailedException(String message) {
		super(message);
	}
}
