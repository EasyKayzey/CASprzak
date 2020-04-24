package core;

@SuppressWarnings("CanBeFinal")
public class Settings {
	private Settings(){}
	//	public static boolean debug = true;
	public static double simpsonsSegments = 500; //MUST BE EVEN
	public static int defaultSolverIterations = 100;
	public static int defaultRangeSections = 29;
	public static double zeroMargin = 1e-3;
	public static boolean simplifyFunctionsOfConstants = true;
	public static boolean distributeExponents = true;
	public static boolean cacheDerivatives = true;
	public static boolean trustImmutability = true; // Makes it so that getFunctions and other getters don't return clone()
}
