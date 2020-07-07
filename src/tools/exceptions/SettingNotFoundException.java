package tools.exceptions;

public class SettingNotFoundException extends IllegalArgumentException {
	/**
	 * To be thrown when an invalid setting is passed to a command
	 * @param setting the invalid setting
	 * @param method the method where the invalid setting was passed
	 */
	public SettingNotFoundException(String setting, String method) {
		super("Setting " + setting + " not found in " + method + ".");
	}
}
