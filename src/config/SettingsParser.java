package config;

import tools.ParsingTools;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class SettingsParser {
	/**
	 * Parses the configuration file cas.properties and stores the read values in {@link Settings}
	 * @throws IOException if the file cannot be found
	 */
	public static void parseConfig() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(".\\src\\config\\cas.properties"));
		for (Map.Entry<Object, Object> entry : properties.entrySet())
			parseSingleSetting((String) entry.getKey(), (String) entry.getValue());
	}

	/**
	 * Parses string input to a single setting to be stored in {@link Settings}
	 * @param key the name of the setting, such as defaultSolverIterations or defaultFactorial
	 * @param value the value of the setting, such as 10000 or RECURSIVE
	 */
	public static void parseSingleSetting(String key, String value) {
		switch (key) {
			case "defaultSolverIterations" 					-> Settings.defaultSolverIterations = Integer.parseInt(value);
			case "defaultRangeSections" 					-> Settings.defaultRangeSections = Integer.parseInt(value);
			case "simpsonsSegments" 						-> Settings.simpsonsSegments = Integer.parseInt(value);
			case "singleVariableDefault" 					-> Settings.singleVariableDefault = ParsingTools.getCharacter(value);
			case "zeroMargin" 								-> Settings.zeroMargin = Double.parseDouble(value);
			case "integerMargin" 							-> Settings.integerMargin = Double.parseDouble((value));
			case "equalsMargin" 							-> Settings.equalsMargin = Double.parseDouble((value));
			case "enforceEscapes"				 			-> Settings.enforceEscapes = ParsingTools.parseBoolean(value);
			case "simplifyFunctionsOfConstants" 			-> Settings.simplifyFunctionsOfConstants = ParsingTools.parseBoolean(value);
			case "distributeExponents" 						-> Settings.distributeExponents = ParsingTools.parseBoolean(value);
			case "cacheDerivatives" 						-> Settings.cacheDerivatives = ParsingTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> Settings.enforceIntegerOperations = ParsingTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> Settings.exitSolverOnProximity = ParsingTools.parseBoolean(value);
			case "executeOnSimplify" 						-> Settings.executeOnSimplify = ParsingTools.parseBoolean(value);
			case "distributeFunctions"					    -> Settings.distributeFunctions = ParsingTools.parseBoolean(value);
			case "defaultSolverType" 						-> Settings.defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> Settings.defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist.");
		}
	}
}
