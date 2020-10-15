package show.ezkz.casprzak.core.tools.defaults;

import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.transforms.Transformation;

/**
 * The {@link DefaultSimplificationSettings} class contains the {@link SimplificationSettings} for minimal, automatic, and aggressive simplification.
 * It also contains the user simplification settings (as read by {@link Settings#parseConfig()} and modified at any time) as public static variables.
 * The {@link #user} settings are regenerated each time {@link #regenerateUser()} is called, which should be done whenever the static settings are changed.
 * The documentation for each setting can be found in the {@link SimplificationSettings} class.
 */
public class DefaultSimplificationSettings {

	// The settings below are the settings for `user`, documented in the `SimplificationSettings` class

	public static boolean simplifyFunctionsOfConstants = true;
	public static boolean simplifyFunctionsOfSpecialConstants = false;
	public static boolean distributeExponentsOverMultiplication = true;
	public static boolean addExponentsInProducts = true;
	public static boolean distributeMultiplicationOverAddition = true;
	public static boolean multiplyExponentsOfExponents = true;
	public static boolean executeTransformsOnSimplify = true;
	public static boolean enforceDomainAndRange = true;
	public static boolean simplifyLogAddition = true;
	public static boolean expandLogOfProducts = false;
	public static boolean extractLogExponents = true;
	public static boolean doChangeOfBase = false;
	public static boolean doTrigIdentities = true;
	public static boolean trigComposition = false;
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
