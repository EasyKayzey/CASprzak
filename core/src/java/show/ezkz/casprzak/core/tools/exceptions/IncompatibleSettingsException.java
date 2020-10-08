package show.ezkz.casprzak.core.tools.exceptions;

public class IncompatibleSettingsException extends IllegalArgumentException {
	/**
	 * To be thrown when incompatible settings are set
	 * @param name1 the name of the first setting
	 * @param val1 the value of the first setting
	 * @param name2 the name of the second setting
	 * @param val2 the value of the second setting
	 */
	public IncompatibleSettingsException(String name1, String val1, String name2, String val2) {
		super("Incompatible settings for " + name1 + " and " + name2 + ": " + val1 + ", " + val2 + ".");
	}
}
