package tools.exceptions;

public class CommandNotFoundException extends IllegalArgumentException {
	public CommandNotFoundException(String message) {
		super(message);
	}
}
