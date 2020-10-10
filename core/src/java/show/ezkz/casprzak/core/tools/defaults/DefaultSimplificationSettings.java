package show.ezkz.casprzak.core.tools.defaults;

import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.unitary.transforms.Transformation;

public class DefaultSimplificationSettings {
	// The settings below are the settings for `user`

	/**
	 * Denotes whether functions of constants should be simplified. Ex: {@code \sin(\pi/2) -> 1}
	 */
	public static boolean simplifyFunctionsOfConstants = true;

	/**
	 * Denotes whether or not special {@code Constant}s get simplified into decimal under addition, multiplication, or exponentiation.
	 */
	public static boolean simplifyFunctionsOfSpecialConstants = false;

	/**
	 * Denotes whether exponents should be distributed over multiplication in {@code simplify(settings)}. Ex: {@code (2x)^2 -> 4x^2}
	 */
	public static boolean distributeExponentsOverMultiplication = true;

	/**
	 *
	 */
	public static boolean addExponentsInProducts = true;

	/**
	 * Denotes whether products are distributed over addition in {@code simplify(settings)}. Ex: {@code x(y+z) -> (xy+xz)}
	 */
	public static boolean distributeMultiplicationOverAddition = true;

	/**
	 *
	 */
	public static boolean multiplyExponentsOfExponents = true;

	/**
	 * Denotes whether a {@link Transformation} should execute its action when {@code simplify(settings)} is called.
	 */
	public static boolean executeTransformsOnSimplify = true;

	/**
	 * Denotes whether or not inverse simplifications conserve domain and range
	 */
	public static boolean enforceDomainAndRange = true;

	/**
	 *
	 */
	public static boolean simplifyLogAddition = true;

	/**
	 *
	 */
	public static boolean expandLogOfProducts = false;

	/**
	 *
	 */
	public static boolean extractLogExponents = true;

	/**
	 *
	 */
	public static boolean insertLogExponents = false;

	/**
	 *
	 */
	public static boolean doChangeOfBase = false;

	/**
	 *
	 */
	public static boolean doTrigIdentities = true;

	/**
	 *
	 */
	public static boolean trigComposition = false;

	/**
	 *
	 */
	public static boolean simplifyInverses = true;


	public static SimplificationSettings user;
	public static final SimplificationSettings minimal = new SimplificationSettings(
			true,
			false,
			false,
			true,
			false,
			false,
			false,
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			false
	);
	public static final SimplificationSettings auto = new SimplificationSettings(
			true,
			false,
			true,
			true,
			false,
			true,
			false,
			true,
			false,
			false,
			false,
			false,
			false,
			false,
			false,
			true
	);
	public static final SimplificationSettings aggressive = new SimplificationSettings(
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			true,
			false,
			true,
			false,
			false,
			true,
			true,
			true
	);

	private DefaultSimplificationSettings() {
		regenerateUser();
	}

	public void regenerateUser() {
		user = new SimplificationSettings(
				simplifyFunctionsOfConstants,
				simplifyFunctionsOfSpecialConstants,
				distributeExponentsOverMultiplication,
				addExponentsInProducts,
				distributeMultiplicationOverAddition,
				multiplyExponentsOfExponents,
				executeTransformsOnSimplify,
				enforceDomainAndRange,
				simplifyLogAddition,
				expandLogOfProducts,
				extractLogExponents,
				insertLogExponents,
				doChangeOfBase,
				doTrigIdentities,
				trigComposition,
				simplifyInverses
		);
	}

}
