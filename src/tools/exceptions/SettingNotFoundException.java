package tools.exceptions;

public class SettingNotFoundException extends IllegalArgumentException {
	public SettingNotFoundException(String setting, String method) {
		super("Setting " + setting + " not found in " + method);
	}
}
