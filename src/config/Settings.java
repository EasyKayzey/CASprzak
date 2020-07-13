package config;

import functions.GeneralFunction;
import functions.unitary.integer.combo.Factorial;
import functions.unitary.transforms.Transformation;
import tools.MiscTools;
import tools.ParsingTools;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * The {@link Settings} class stores global settings as static variables. These settings are read from {@code cas.properties} by {@link Settings#parseConfig()} on launch, and can be modified dynamically during runtime.
 * Settings that are modified during runtime do NOT get written into the properties file. To save a settings profile, write it into {@code cas.properties} so it is read on launch.
 * The class also contains tools for parsing settings from files and user input, then storing those settings for use by package methods.
 */
@SuppressWarnings("CanBeFinal")
public class Settings {

	/**
	 * When this setting is enabled, {@link Settings} uses {@link #parseConfig()} to read {@code cas.properties} and those values are stored in this class.
	 */
	public static boolean readProperties = true;

	private Settings(){}

	/**
	 * The amount of times that {@link tools.singlevariable.Solver} will run unless the exit conditions are met beforehand.
	 */
	public static int defaultSolverIterations = 100;

	/**
	 * The amount of segments which {@link tools.singlevariable.Solver} splits a given range into.
	 */
	public static int defaultRangeSections = 29;

	/**
	 * The number of segments that {@link tools.singlevariable.NumericalIntegration#simpsonsRule(GeneralFunction, double, double)} uses when performing numerical integration. **MUST BE EVEN**
	 */
	public static int simpsonsSegments = 500;

	/*
	 * The maximum length that a LaTeX escape extends before expiring. This setting can only be changed through the config; modifying it during runtime will not do anything.
	 */
	public static int maxEscapeLength = 8;

	/**
	 * The variable to be used in {@link tools.singlevariable} when none is specified.
	 */
	public static String singleVariableDefault = "x";

	/**
	 * The margin that {@link tools.singlevariable.Solver} uses to determine if a value is close enough to zero to be considered a root of a function.
	 */
	public static double zeroMargin = 1E-3;

	/**
	 * The margin used in {@link ParsingTools#toInteger(double)} when deciding when a {@code double} is close enough to an integer.
	 */
	public static double integerMargin = 1E-4;

	/**
	 * The default margin to be used when checking if two doubles are equal.
	 */
	public static double equalsMargin = 1E-10;

	/**
	 * The default amount to be slept on newlines in {@link MiscTools#printWithSleep}.
	 */
	public static double defaultSleep = 1.5;

	/**
	 * Denotes whether or not expressions like {@code sin(pi/2)} must be escaped to {@code \sin(\pi/2)}. Enabling this is strongly recommended, and may reduce bugs.
	 */
	public static boolean enforceEscapedFunctions = false;

	/**
	 * Denotes whether or not the regex for valid variable, function, and constant names should enforce a LaTeX escape in multi-character names. Enabling this may improve error handling.
	 */
	public static boolean enforceEscapedNames = true;

	/**
	 * Denotes whether or not variable, function, and constant names should be checked against the valid name regex {@link ParsingTools#validNames}
	 */
	public static boolean enforcePatternMatchingNames = true;

	/**
	 * Denotes whether or not escapes should be removed from variable, function, and constant names when printing.
	 */
	public static boolean removeEscapes = false;

	/**
	 * Denotes whether functions of constants should be simplified. Ex: {@code \sin(\pi/2) -> 1}
	 */
	public static boolean simplifyFunctionsOfConstants = true;

	/**
	 * Denotes whether or not special Constants get simplified into decimal under addition, multiplication, or exponentiation.
	 */
	public static boolean simplifyFunctionsOfSpecialConstants = false;

	/**
	 * Denotes whether exponents should be distributed over multiplication in {@code simplify()}. Ex: {@code (2x)^2 -> 4x^2}
	 */
	public static boolean distributeExponents = true;

	/**
	 * Denotes whether or not the derivatives of functions should be cached when created.
	 */
	public static boolean cacheDerivatives = true;

	/**
	 * Forces functions in {@link functions.unitary.integer} to return integers when using approximations.
	 */
	public static boolean enforceIntegerOperations = true;

	/**
	 * Denotes whether methods in {@link tools.singlevariable} should exit if the result is within a certain proximity of the target. Improves performance at the cost of accuracy.
	 */
	public static boolean exitSolverOnProximity = false;

	/**
	 * Denotes whether a {@link Transformation} should execute its action when {@code simplify()} is called.
	 */
	public static boolean executeOnSimplify = true;

	/**
	 * Denotes whether products are distributed over addition in {@code simplify()}. Ex: {@code x(y+z) -> (xy+xz)}
	 */
	public static boolean distributeFunctions = true;

	/**
	 * Denotes whether full stack traces of errors should be printed for debugging.
	 */
	public static boolean printStackTraces = false;

	/**
	 * Denotes whether or not inverse simplifications conserve domain and range
	 */
	public static boolean enforceDomainAndRange = false;

	/**
	 * Denotes whether or not multiplication should be delimited by asterisks (as in `2 * x`) or not (as in `2x`)
	 */
	public static boolean asteriskMultiplication = false;

	/**
	 * Denotes whether or not constants close to integers are printed as integers (zero not included)
	 */
	public static boolean truncateNearIntegers = true;

	/**
	 * Denotes whether or not combinatorial operations should be parsed. This setting can only be changed through the config; modifying it during runtime will not do anything.
	 */
	public static boolean doCombinatorics = true;

	/**
	 * Denotes the default method used to solve equations.
	 */
	public static SolverType defaultSolverType = SolverType.NEWTON;

	/**
	 * Denotes the default implementation of {@link Factorial} to be used.
	 */
	public static FactorialType defaultFactorial = FactorialType.RECURSIVE;


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
			case "defaultSolverIterations" 					-> defaultSolverIterations = Integer.parseInt(value);
			case "defaultRangeSections" 					-> defaultRangeSections = Integer.parseInt(value);
			case "simpsonsSegments" 						-> simpsonsSegments = Integer.parseInt(value);
			case "maxEscapeLength" 							-> maxEscapeLength = Integer.parseInt(value);
			case "singleVariableDefault" 					-> singleVariableDefault = value;
			case "zeroMargin" 								-> zeroMargin = Double.parseDouble(value);
			case "integerMargin" 							-> integerMargin = Double.parseDouble(value);
			case "equalsMargin" 							-> equalsMargin = Double.parseDouble(value);
			case "defaultSleep"								-> defaultSleep = Double.parseDouble(value);
			case "enforceEscapedFunctions"		 			-> enforceEscapedFunctions = ParsingTools.parseBoolean(value);
			case "enforceEscapedNames"		 				-> enforceEscapedNames = ParsingTools.parseBoolean(value);
			case "enforcePatternMatchingNames"		 		-> enforcePatternMatchingNames = ParsingTools.parseBoolean(value);
			case "removeEscapes"		 					-> removeEscapes = ParsingTools.parseBoolean(value);
			case "simplifyFunctionsOfConstants" 			-> simplifyFunctionsOfConstants = ParsingTools.parseBoolean(value);
			case "simplifyFunctionsOfSpecialConstants"		-> simplifyFunctionsOfSpecialConstants = ParsingTools.parseBoolean(value);
			case "distributeExponents" 						-> distributeExponents = ParsingTools.parseBoolean(value);
			case "cacheDerivatives" 						-> cacheDerivatives = ParsingTools.parseBoolean(value);
			case "enforceIntegerOperations" 				-> enforceIntegerOperations = ParsingTools.parseBoolean(value);
			case "exitSolverOnProximity" 					-> exitSolverOnProximity = ParsingTools.parseBoolean(value);
			case "executeOnSimplify" 						-> executeOnSimplify = ParsingTools.parseBoolean(value);
			case "distributeFunctions"					    -> distributeFunctions = ParsingTools.parseBoolean(value);
			case "printStackTraces"						    -> printStackTraces = ParsingTools.parseBoolean(value);
			case "enforceDomainAndRange"					-> enforceDomainAndRange = ParsingTools.parseBoolean(value);
			case "asteriskMultiplication"					-> asteriskMultiplication = ParsingTools.parseBoolean(value);
			case "truncateNearIntegers" 					-> truncateNearIntegers = ParsingTools.parseBoolean(value);
			case "doCombinatorics"							-> doCombinatorics = ParsingTools.parseBoolean(value);
			case "defaultSolverType" 						-> defaultSolverType = SolverType.valueOf(value);
			case "defaultFactorial" 						-> defaultFactorial = FactorialType.valueOf(value);
			default 										-> throw new IllegalStateException("Setting " + key + " does not exist in settings parser.");
		}
	}
}
