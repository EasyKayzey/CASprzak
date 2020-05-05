package tools.exceptions;

public class NotYetImplementedException extends UnsupportedOperationException{
	/**
	 * To be thrown when a feature is not yet implemented
	 * @param message the feature not yet implemented
	 */
	public NotYetImplementedException(String message) {
		super(message);
	}
}
