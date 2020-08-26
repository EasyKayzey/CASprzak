package show.ezkz.casprzak.core.tools.exceptions;

public class CommandNotFoundException extends IllegalArgumentException {
	/**
	 * To be thrown when an invalid command is passed to KeywordInterface
	 * @param message the message describing the problem
	 */
	public CommandNotFoundException(String message) {
		super(message);
	}

	/**
	 * To be thrown when an invalid command is passed to KeywordInterface
	 * @param message the message describing the problem
	 * @param cause the cause of the error
	 */
	public CommandNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
