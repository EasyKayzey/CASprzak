package core;

@SuppressWarnings("CanBeFinal")
public class Settings {
//	public static boolean debug = true;
	public static boolean simplifyFunctionsOfConstants = true;
	public static boolean distributeExponents = true;
	public static boolean cacheDerivatives = true;
	public static boolean trustImmutability = true; // Makes it so that getFunctions and other getters don't return clone()
}
