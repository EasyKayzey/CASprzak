package tools.exceptions;

public class CommandNotFoundException extends IllegalArgumentException {
	/**
	 * To be thrown when an invalid command is passed to KeywordInterface
	 * @param message the message describing the problem
	 */
	public CommandNotFoundException(String message) {
		super(message);
	}
}
