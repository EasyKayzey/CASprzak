package config;

@SuppressWarnings("CanBeFinal")
public class Settings {
	private Settings(){}
	//	public static boolean debug = true;
	public static int defaultSolverIterations = 100;
	public static int defaultRangeSections = 29;
	public static double simpsonsSegments = 500; // MUST BE EVEN
	public static double zeroMargin = 1e-3;
	public static double integerMargin = 1e-4;
	public static double equalsMargin = 1e-12;
	public static boolean simplifyFunctionsOfConstants = true;
	public static boolean distributeExponents = true;
	public static boolean cacheDerivatives = true;
	public static boolean trustImmutability = true; // Makes it so that getFunctions and other getters don't return clone()
	public static boolean enforceIntegerOperations = true; // Makes it so that combinatorial operations return integers
	public static boolean exitSolverOnProximity = false;
	public static char singleVariableDefault = 'x';
	public static SolverType defaultSolverType = SolverType.NEWTON;
	public static FactorialType defaultFactorial = FactorialType.RECURSIVE;
}
