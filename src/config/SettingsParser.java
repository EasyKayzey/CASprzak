package config;

import tools.MiscTools;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class SettingsParser {
	public static void parseConfig() throws IOException {
		Properties properties = new Properties();
		properties.load(new FileReader(".\\src\\config\\cas.properties"));
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			switch ((String) entry.getKey()) {
				case "defaultSolverIterations" -> Settings.defaultSolverIterations = Integer.parseInt((String) entry.getValue());
				case "defaultRangeSections" -> Settings.defaultRangeSections = Integer.parseInt((String) entry.getValue());
				case "simpsonsSegments" -> Settings.simpsonsSegments = Integer.parseInt((String) entry.getValue());
				case "singleVariableDefault" -> Settings.singleVariableDefault = MiscTools.getCharacter((String) entry.getValue());
				case "zeroMargin" -> Settings.zeroMargin = Double.parseDouble((String) entry.getValue());
				case "integerMargin" -> Settings.integerMargin = Double.parseDouble(((String) entry.getValue()));
				case "equalsMargin" -> Settings.equalsMargin = Double.parseDouble(((String) entry.getValue()));
				case "simplifyFunctionsOfConstants" -> Settings.simplifyFunctionsOfConstants = MiscTools.parseBoolean((String) entry.getValue());
				case "distributeExponents" -> Settings.distributeExponents =  MiscTools.parseBoolean((String) entry.getValue());
				case "cacheDerivatives" -> Settings.cacheDerivatives =  MiscTools.parseBoolean((String) entry.getValue());
				case "trustImmutability" -> Settings.trustImmutability =  MiscTools.parseBoolean((String) entry.getValue());
				case "enforceIntegerOperations" -> Settings.enforceIntegerOperations =  MiscTools.parseBoolean((String) entry.getValue());
				case "exitSolverOnProximity" -> Settings.exitSolverOnProximity =  MiscTools.parseBoolean((String) entry.getValue());
				case "defaultSolverType" -> Settings.defaultSolverType = SolverType.valueOf((String) entry.getValue());
				case "defaultFactorial" -> Settings.defaultFactorial = FactorialType.valueOf((String) entry.getValue());
				default -> throw new IllegalStateException("Setting " + entry.getKey() + "does not exist.");
			}
		}
	}
}
