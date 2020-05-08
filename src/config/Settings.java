package config;

import functions.GeneralFunction;

import java.io.IOException;

@SuppressWarnings("CanBeFinal")
public class Settings {

	/**
	 * When this setting is enabled, Settings uses {@link SettingsParser} to read cas.properties and those values are stored in this class.
	 */
	public static boolean readProperties = true;

	static {
		//noinspection ConstantConditions
		if (readProperties) {
			try {
				SettingsParser.parseConfig();
			} catch (IOException e) {
				System.out.println("Properties file not found. Using defaults...");
			}
		}
	}

	private Settings(){}

	/**
	 * The amount of times that {@link tools.singlevariable.Solver} will run unless the exit conditions are met beforehand
	 */
	public static int defaultSolverIterations = 100;

	/**
	 * The amount of segments which {@link tools.singlevariable.Solver} splits a given range into
	 */
	public static int defaultRangeSections = 29;

	/**
	 * The number of segments that {@link tools.singlevariable.NumericalIntegration#simpsonsRule(GeneralFunction, double, double)} uses when performing numerical integration. **MUST BE EVEN**
	 */
	public static int simpsonsSegments = 500; // MUST BE EVEN

	/**
	 * The variable to be used in {@link tools.singlevariable} when none is specified
	 */
	public static char singleVariableDefault = 'x';

	/**
	 * The margin that {@link tools.singlevariable.Solver} uses to determine if a values is close enough to zero to considered zero
	 */
	public static double zeroMargin = 1e-3;

	/**
	 * The margin used in {@link tools.MiscTools#toInteger(double)} when deciding when a double is close enough to an integer
	 */
	public static double integerMargin = 1e-4;

	/**
	 * The default margin to be used when checking if two doubles are equal
	 */
	public static double equalsMargin = 1e-12;

	/**
	 * Denotes whether or not expressions like sin(pi/2) must be escaped to \sin(\pi/2). Enabling this will significantly reduce issues.
	 */
	public static boolean enforceEscapes = false;

	/**
	 * Denotes whether functions of constants should be simplified, e.g. \sin(\pi/2) -> 1
	 */
	public static boolean simplifyFunctionsOfConstants = true;

	/**
	 * Denotes whether exponents should be distributed over multiplication in a normal simplify(), e.g. (2x)^2 -> 4x^2
	 */
	public static boolean distributeExponents = true;

	/**
	 * Denotes whether or not the derivatives of functions should be cached when created
	 */
	public static boolean cacheDerivatives = true;

	/**
	 * Denotes whether or not function immutability should be trusted when using getters or performing simplifications. There should be no reason to turn this off.
	 */
	public static boolean trustImmutability = true; // TODO REMOVE THIS!!!!

	/**
	 * Forces functions in {@link functions.unitary.combo} to return integers when using approximations
	 */
	public static boolean enforceIntegerOperations = true;

	/**
	 * Denotes whether methods in {@link tools.singlevariable} should exit if the result is within a certain proximity of the target. Improves performance at the cost of accuracy.
	 */
	public static boolean exitSolverOnProximity = false;

	/**
	 * Denotes the default method used to solve equations
	 */
	public static SolverType defaultSolverType = SolverType.NEWTON;

	/**
	 * Denotes the default implementation of factorial to be used
	 */
	public static FactorialType defaultFactorial = FactorialType.RECURSIVE;
}
