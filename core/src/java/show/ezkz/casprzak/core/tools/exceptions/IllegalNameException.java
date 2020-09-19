package show.ezkz.casprzak.core.tools.exceptions;

import show.ezkz.casprzak.core.config.Settings;

public class IllegalNameException extends IllegalArgumentException {
	public IllegalNameException(String name) {
		super(
				"Invalid variable, constant, or function name '" + name + "'. " +
				"If this is not user error, it may indicate a splitting or parsing failure. " +
				"Valid names are a single letter character, or an escaped letter character followed by up to " + (Settings.maxEscapeLength - 2) + " letters, numbers, periods, apostrophes, or underscores."
		);
	}
}
