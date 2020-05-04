package config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SettingsParser {
	public static void parseConfig() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(".\\src\\config\\settings.config"));
		for (Object propertyObject : properties.keySet()) {
			if (propertyObject instanceof String property) {

			} else {
				throw new IllegalStateException("This should not happen");
			}
		}
	}
}
