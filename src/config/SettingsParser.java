package config;

import tools.MiscTools;
import tools.helperclasses.Pair;

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
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			parseSingleSetting(new Pair<>((String) entry.getKey(), (String) entry.getValue()));
		}
	}

	public static void parseSingleSetting(Pair<String, String> pair) {
		switch (pair.getFirst()) {
			case "defaultSolverIterations" -> Settings.defaultSolverIterations = Integer.parseInt(pair.getSecond());
			case "defaultRangeSections" -> Settings.defaultRangeSections = Integer.parseInt(pair.getSecond());
			case "simpsonsSegments" -> Settings.simpsonsSegments = Integer.parseInt(pair.getSecond());
			case "singleVariableDefault" -> Settings.singleVariableDefault = MiscTools.getCharacter(pair.getSecond());
			case "zeroMargin" -> Settings.zeroMargin = Double.parseDouble(pair.getSecond());
			case "integerMargin" -> Settings.integerMargin = Double.parseDouble((pair.getSecond()));
			case "equalsMargin" -> Settings.equalsMargin = Double.parseDouble((pair.getSecond()));
			case "simplifyFunctionsOfConstants" -> Settings.simplifyFunctionsOfConstants = MiscTools.parseBoolean(pair.getSecond());
			case "distributeExponents" -> Settings.distributeExponents =  MiscTools.parseBoolean(pair.getSecond());
			case "cacheDerivatives" -> Settings.cacheDerivatives =  MiscTools.parseBoolean(pair.getSecond());
			case "trustImmutability" -> Settings.trustImmutability =  MiscTools.parseBoolean(pair.getSecond());
			case "enforceIntegerOperations" -> Settings.enforceIntegerOperations =  MiscTools.parseBoolean(pair.getSecond());
			case "exitSolverOnProximity" -> Settings.exitSolverOnProximity =  MiscTools.parseBoolean(pair.getSecond());
			case "defaultSolverType" -> Settings.defaultSolverType = SolverType.valueOf(pair.getSecond());
			case "defaultFactorial" -> Settings.defaultFactorial = FactorialType.valueOf(pair.getSecond());
			default -> throw new IllegalStateException("Setting " + pair.getFirst() + "does not exist.");
		}
	}
}
