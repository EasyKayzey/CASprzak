package show.ezkz.casprzak.core.config;

import show.ezkz.casprzak.core.tools.ParsingTools;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * The {@link CoreSettingsParser} class reads settings from strings and properties files and stores them in their corresponding
 */
public class CoreSettingsParser {

	private CoreSettingsParser(){}

	/**
	 * Parses the global configuration file and stores the read values in {@link Settings}
	 * @param config the properties file to be read
	 * @throws IOException if {@link Properties#load} fails
	 */
	public static void parseGlobalConfig(File config) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(config));
		for (Map.Entry<Object, Object> entry : properties.entrySet())
			parseSingleGlobalSetting((String) entry.getKey(), (String) entry.getValue());
	}

	/**
	 * Parses string input to a single setting to be stored in {@link Settings}, then calls {@link DefaultSimplificationSettings#regenerateUser()}
	 * @param key the name of the setting, such as defaultSolverIterations or defaultFactorial
	 * @param value the value of the setting, such as {@code 10000} or {@code RECURSIVE}
	 */
	public static void parseSingleGlobalSetting(String key, String value) { // TODO add new settings
		switch (key) {
			case "defaultSolverIterations" 					-> Settings.defaultSolverIterations = Integer.parseInt(value);
			case "defaultRangeSections" 					-> Settings.defaultRangeSections = Integer.parseInt(value);
			case "simpsonsSegments" 						-> Settings.simpsonsSegments = Integer.parseInt(value);
			case "maxEscapeLength" 							-> Settings.maxEscapeLength = Integer.parseInt(value);
			case "singleVariableDefault" 					-> Settings.singleVariableDefault = value;
			case "zeroMargin" 								-> Settings.zeroMargin = Double.parseDouble(value);
			case "integerMargin" 							-> Settings.integerMargin = Double.parseDouble(value);
			case "equalsMargin" 							-> Settings.equalsMargin = Double.parseDouble(value);
			case "defaultSleep"								-> Settings.defaultSleep = Double.parseDouble(value);
			case "enforceEscapes"				 			-> Settings.enforceEscapes = ParsingTools.parseBoolean(value);
			case "escapedNames"				 				-> Settings.escapeNames = ParsingTools.parseBoolean(value);
			case "enforcePatternMatchingNames"		 		-> Settings.enforcePatternMatchingNames = ParsingTools.parseBoolean(value);
			case "removeEscapes"		 					-> Settings.removeEscapes = ParsingTools.parseBoolean(value);
			case "cacheDerivatives" 						-> Settings.cacheDerivatives = ParsingTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> Settings.enforceIntegerOperations = ParsingTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> Settings.exitSolverOnProximity = ParsingTools.parseBoolean(value);
			case "printStackTraces"						    -> Settings.printStackTraces = ParsingTools.parseBoolean(value);
			case "asteriskMultiplication"					-> Settings.asteriskMultiplication = ParsingTools.parseBoolean(value);
			case "truncateNearIntegers" 					-> Settings.truncateNearIntegers = ParsingTools.parseBoolean(value);
			case "doCombinatorics"							-> Settings.doCombinatorics = ParsingTools.parseBoolean(value);
			case "defaultSolverType" 						-> Settings.defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> Settings.defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist in settings parser.");
		}
	}


	/**
	 * Parses the simplification configuration file and stores the read values in {@link DefaultSimplificationSettings}
	 * @param config the properties file to be read
	 * @throws IOException if {@link Properties#load} fails
	 */
	public static void parseSimplificationConfig(File config) throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(config));
		for (Map.Entry<Object, Object> entry : properties.entrySet())
			parseSingleSimplificationSetting((String) entry.getKey(), (String) entry.getValue(), false);
		DefaultSimplificationSettings.regenerateUser();
	}

	/**
	 * Parses string input to a single setting to be stored in {@link DefaultSimplificationSettings}, then calls {@link DefaultSimplificationSettings#regenerateUser()}
	 * @param key the name of the setting, such as defaultSolverIterations or defaultFactorial TODO fix names and examples
	 * @param value the value of the setting, such as {@code 10000} or {@code RECURSIVE}
	 */
	public static void parseSingleSimplificationSetting(String key, String value) {
		parseSingleSimplificationSetting(key, value, true);
	}

	/**
	 * Parses string input to a single setting to be stored in {@link DefaultSimplificationSettings}
	 * @param key the name of the setting, such as defaultSolverIterations or defaultFactorial TODO fix names and examples
	 * @param value the value of the setting, such as {@code 10000} or {@code RECURSIVE}
	 * @param regenerateUserAfterParsing denotes whether or not {@link DefaultSimplificationSettings#regenerateUser()} should be called after parsing
	 */
	public static void parseSingleSimplificationSetting(String key, String value, boolean regenerateUserAfterParsing) {
		switch (key) {
			case "defaultSolverIterations" 					-> Settings.defaultSolverIterations = Integer.parseInt(value);
			case "defaultRangeSections" 					-> Settings.defaultRangeSections = Integer.parseInt(value);
			case "simpsonsSegments" 						-> Settings.simpsonsSegments = Integer.parseInt(value);
			case "maxEscapeLength" 							-> Settings.maxEscapeLength = Integer.parseInt(value);
			case "singleVariableDefault" 					-> Settings.singleVariableDefault = value;
			case "zeroMargin" 								-> Settings.zeroMargin = Double.parseDouble(value);
			case "integerMargin" 							-> Settings.integerMargin = Double.parseDouble(value);
			case "equalsMargin" 							-> Settings.equalsMargin = Double.parseDouble(value);
			case "defaultSleep"								-> Settings.defaultSleep = Double.parseDouble(value);
			case "enforceEscapes"				 			-> Settings.enforceEscapes = ParsingTools.parseBoolean(value);
			case "escapedNames"				 				-> Settings.escapeNames = ParsingTools.parseBoolean(value);
			case "enforcePatternMatchingNames"		 		-> Settings.enforcePatternMatchingNames = ParsingTools.parseBoolean(value);
			case "removeEscapes"		 					-> Settings.removeEscapes = ParsingTools.parseBoolean(value);
			case "cacheDerivatives" 						-> Settings.cacheDerivatives = ParsingTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> Settings.enforceIntegerOperations = ParsingTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> Settings.exitSolverOnProximity = ParsingTools.parseBoolean(value);
			case "printStackTraces"						    -> Settings.printStackTraces = ParsingTools.parseBoolean(value);
			case "asteriskMultiplication"					-> Settings.asteriskMultiplication = ParsingTools.parseBoolean(value);
			case "truncateNearIntegers" 					-> Settings.truncateNearIntegers = ParsingTools.parseBoolean(value);
			case "doCombinatorics"							-> Settings.doCombinatorics = ParsingTools.parseBoolean(value);
			case "defaultSolverType" 						-> Settings.defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> Settings.defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist in settings parser.");
		}
		if (regenerateUserAfterParsing)
			DefaultSimplificationSettings.regenerateUser();
	}
}
