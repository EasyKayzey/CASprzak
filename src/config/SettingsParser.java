package config;

import tools.MiscTools;

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

	public static void parseSingleSetting(String key, String value) {
		switch (key) {
			case "defaultSolverIterations" 					-> Settings.defaultSolverIterations = Integer.parseInt(value);
			case "defaultRangeSections" 					-> Settings.defaultRangeSections = Integer.parseInt(value);
			case "simpsonsSegments" 						-> Settings.simpsonsSegments = Integer.parseInt(value);
			case "singleVariableDefault" 					-> Settings.singleVariableDefault = MiscTools.getCharacter(value);
			case "zeroMargin" 								-> Settings.zeroMargin = Double.parseDouble(value);
			case "integerMargin" 							-> Settings.integerMargin = Double.parseDouble((value));
			case "equalsMargin" 							-> Settings.equalsMargin = Double.parseDouble((value));
			case "enforceEscapes"				 			-> Settings.enforceEscapes = MiscTools.parseBoolean(value);
			case "simplifyFunctionsOfConstants" 			-> Settings.simplifyFunctionsOfConstants = MiscTools.parseBoolean(value);
			case "distributeExponents" 						-> Settings.distributeExponents = MiscTools.parseBoolean(value);
			case "cacheDerivatives" 						-> Settings.cacheDerivatives = MiscTools.parseBoolean(value);
			case "trustImmutability" 						-> Settings.trustImmutability = MiscTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> Settings.enforceIntegerOperations = MiscTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> Settings.exitSolverOnProximity = MiscTools.parseBoolean(value);
			case "defaultSolverType" 						-> Settings.defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> Settings.defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist.");
		}
	}
}
