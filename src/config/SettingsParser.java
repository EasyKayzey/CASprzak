package config;

import tools.ParsingTools;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * {@link SettingsParser} contains tools for parsing settings from files and user input, then storing those settings in {@link Settings} for use by package methods.
 */
public class SettingsParser {
	/**
	 * Parses the configuration file {@code cas.properties} and stores the read values in {@link Settings}
	 * @throws IOException if the file cannot be found
	 */
	public static void parseConfig() throws IOException {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(".\\src\\config\\cas.properties"));
		} catch (IOException ignored) {
			properties.load(new FileReader(".\\cas.properties"));
		}
		for (Map.Entry<Object, Object> entry : properties.entrySet())
			parseSingleSetting((String) entry.getKey(), (String) entry.getValue());
	}

	/**
	 * Parses string input to a single setting to be stored in {@link Settings}
	 * @param key the name of the setting, such as defaultSolverIterations or defaultFactorial
	 * @param value the value of the setting, such as {@code 10000} or {@code RECURSIVE}
	 */
	public static void parseSingleSetting(String key, String value) {
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
			case "enforceEscapedFunctions"		 			-> Settings.enforceEscapedFunctions = ParsingTools.parseBoolean(value);
			case "enforceEscapedNames"		 				-> Settings.enforceEscapedNames = ParsingTools.parseBoolean(value);
			case "removeEscapes"		 					-> Settings.removeEscapes = ParsingTools.parseBoolean(value);
			case "simplifyFunctionsOfConstants" 			-> Settings.simplifyFunctionsOfConstants = ParsingTools.parseBoolean(value);
			case "distributeExponents" 						-> Settings.distributeExponents = ParsingTools.parseBoolean(value);
			case "cacheDerivatives" 						-> Settings.cacheDerivatives = ParsingTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> Settings.enforceIntegerOperations = ParsingTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> Settings.exitSolverOnProximity = ParsingTools.parseBoolean(value);
			case "executeOnSimplify" 						-> Settings.executeOnSimplify = ParsingTools.parseBoolean(value);
			case "distributeFunctions"					    -> Settings.distributeFunctions = ParsingTools.parseBoolean(value);
			case "printStackTraces"						    -> Settings.printStackTraces = ParsingTools.parseBoolean(value);
			case "enforceDomainAndRange"					-> Settings.enforceDomainAndRange = ParsingTools.parseBoolean(value);
			case "doCombinatorics"							-> Settings.doCombinatorics = ParsingTools.parseBoolean(value);
			case "simplifyFunctionOfSpecialConstants"		-> Settings.simplifyFunctionsOfSpecialConstants = ParsingTools.parseBoolean(value);
			case "defaultSolverType" 						-> Settings.defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> Settings.defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist in SettingsParser.");
		}
	}
}
