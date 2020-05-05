package tools.exceptions;

public class IntegrationFailedException extends UnsupportedOperationException {
	/**
	 * To be thrown when integration has failed
	 * @param message more information about failed integration
	 */
	public IntegrationFailedException(String message) {
		super(message);
	}
}
