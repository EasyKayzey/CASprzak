package show.ezkz.casprzak.core.config;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.unitary.integer.combo.Factorial;
import show.ezkz.casprzak.core.tools.MiscTools;
import show.ezkz.casprzak.core.tools.ParsingTools;

import java.io.File;

/**
 * The {@link Settings} class stores global settings as static variables. These settings are read from {@code cas.properties} by {@link CoreSettingsParser#parseGlobalProperties(File)} on launch, and can be modified dynamically during runtime.
 * Settings that are modified during runtime do NOT get written into the properties file. To save a settings profile, write it into {@code cas.properties} so it is read on launch.
 * The class also contains tools for parsing settings from files and user input, then storing those settings for use by package methods.
 */
@SuppressWarnings("CanBeFinal")
public class Settings {

	/**
	 * When this setting is enabled, {@link Settings} uses {@link CoreSettingsParser#parseGlobalProperties(File)} to read {@code cas.properties} and stores those values in this class.
	 */
	public static boolean readProperties = true;

	private Settings(){}

	/**
	 * The amount of times that {@link show.ezkz.casprzak.core.tools.singlevariable.Solver} will run unless the exit conditions are met beforehand.
	 */
	public static int defaultSolverIterations = 100;

	/**
	 * The amount of segments which {@link show.ezkz.casprzak.core.tools.singlevariable.Solver} splits a given range into.
	 */
	public static int defaultRangeSections = 29;

	/**
	 * The number of segments that {@link show.ezkz.casprzak.core.tools.singlevariable.NumericalIntegration#simpsonsRule(GeneralFunction, double, double)} uses when performing numerical integration. **MUST BE EVEN**
	 */
	public static int simpsonsSegments = 500;

	/**
	 * The maximum length that a LaTeX escape extends before expiring. This setting can only be changed through the config; modifying it during runtime will not do anything.
	 */
	public static int maxEscapeLength = 8;

	/**
	 * The variable to be used in {@link show.ezkz.casprzak.core.tools.singlevariable} when none is specified.
	 */
	public static String singleVariableDefault = "x";

	/**
	 * The margin that {@link show.ezkz.casprzak.core.tools.singlevariable.Solver} uses to determine if a value is close enough to zero to be considered a root of a function.
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
	public static boolean enforceEscapes = false;

	/**
	 * Denotes whether or not the regex for valid variable, function, and constant names should enforce a LaTeX escape in multi-character names. Enabling this may improve error handling.
	 */
	public static boolean escapeNames = true;

	/**
	 * Denotes whether or not variable, function, and constant names should be checked against the valid name regex {@link ParsingTools#validNames}
	 */
	public static boolean enforcePatternMatchingNames = true;

	/**
	 * Denotes whether or not escapes should be removed from variable, function, and constant names when printing.
	 */
	public static boolean removeEscapes = false;

	/**
	 * Denotes whether or not the derivatives of functions should be cached when created.
	 */
	public static boolean cacheDerivatives = true;

	/**
	 * Forces functions in {@link show.ezkz.casprzak.core.functions.unitary.integer} to return integers when using approximations.
	 */
	public static boolean enforceIntegerOperations = true;

	/**
	 * Denotes whether methods in {@link show.ezkz.casprzak.core.tools.singlevariable} should exit if the result is within a certain proximity of the target. Improves performance at the cost of accuracy.
	 */
	public static boolean exitSolverOnProximity = false;

	/**
	 * Denotes whether full stack traces of errors should be printed for debugging.
	 */
	public static boolean printStackTraces = false;

	/**
	 * Denotes whether or not multiplication should be delimited by asterisks (as in {@code 2 * x}) or not (as in {@code 2x})
	 */
	public static boolean asteriskMultiplication = true;

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


}
