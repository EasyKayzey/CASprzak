package show.ezkz.casprzak.core.tools.defaults;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.transforms.Transformation;

/**
 * The {@link DefaultSimplificationSettings} class contains the simplification settings for minimal, automatic, and aggressive simplification.
 * It also contains the user simplification settings (as read by {@link Settings#parseConfig()} and modified at any time) as public static variables.
 * The {@link #user} settings are regenerated each time {@link #regenerateUser()} is called, which should be done whenever the static settings are changed.
 */
public class DefaultSimplificationSettings {

	// The settings below are the settings for `user`

	/**
	 * Denotes whether functions of constants should be simplified. Ex: {@code 2*3^2 -> 18}
	 */
	public static boolean simplifyFunctionsOfConstants = true;

	/**
	 * Denotes whether or not special {@code Constant}s get simplified into decimal under addition, multiplication, or exponentiation. Ex: {@code \pi^2 -> 9.87}
	 */
	public static boolean simplifyFunctionsOfSpecialConstants = false;

	/**
	 * Denotes whether exponents should be distributed over multiplication in {@code simplify(settings)}. Ex: {@code (2x)^2 -> 4x^2}
	 */
	public static boolean distributeExponentsOverMultiplication = true;

	/**
	 * Denotes whether like terms in a product should have their exponents added. Ex: {@code x*x^2 -> x^3}
	 */
	public static boolean addExponentsInProducts = true;

	/**
	 * Denotes whether products are distributed over addition in {@code simplify(settings)}. Ex: {@code x(y+z) -> (xy+xz)}
	 */
	public static boolean distributeMultiplicationOverAddition = true;

	/**
	 * Denotes whether nested powers should have their exponents multiplied. Ex: {@code (x^3)^2 -> x^6}
	 */
	public static boolean multiplyExponentsOfExponents = true;

	/**
	 * Denotes whether a {@link Transformation} should execute its action when {@code simplify(settings)} is called. Ex: {@code d/dx[x^2] -> 2x} (without explicit execution)
	 */
	public static boolean executeTransformsOnSimplify = true;

	/**
	 * Denotes whether or not inverse simplifications conserve domain and range. Ex: {@code asin(sin(7*\pi/2)) -> -\pi/2} (without evaluation)
	 */
	public static boolean enforceDomainAndRange = true;

	/**
	 * Denotes whether sums of logs should become products of their arguments. Ex: {@code ln(x) + ln(y) -> ln(x*y)}
	 */
	public static boolean simplifyLogAddition = true;

	/**
	 * Denotes whether products inside logs should become sums of logs. Ex: {@code ln(x*y) -> ln(x) + ln(y)}
	 */
	public static boolean expandLogOfProducts = false;

	/**
	 * Denotes whether exponents inside of logs are pulled out. Ex. {@code ln(x^x) = xln(x)}
	 */
	public static boolean extractLogExponents = true;

	/**
	 * Denotes whether or not {@link show.ezkz.casprzak.core.tools.algebra.LogSimplify#logChainRule(SimplificationSettings, Product)} is performed. Ex. Ex: {@code logb_a(b) * logb_b(c) * logb_x(y) / logb_x(z) = logb_a(c) * logb_z(y)}
	 */
	public static boolean doChangeOfBase = false;

	/**
	 * Denotes whether trig identities are performed. Ex. {@code sin^2(x) + cos^2(x) = 1 }
	 */
	public static boolean doTrigIdentities = true;

	/**
	 * Denotes whether trig composition simplify to algebraic expressions. Ex. {@code sin(arccos(x)) = \sqrt(1-x^2)}
	 */
	public static boolean trigComposition = false;

	/**
	 * Denotes whether inverse should cancel. Ex {@code ln(e^x)=x}
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
				doChangeOfBase,
				doTrigIdentities,
				trigComposition,
				simplifyInverses
		);
	}

}
