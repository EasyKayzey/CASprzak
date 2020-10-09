package show.ezkz.casprzak.core.tools.defaults;

import show.ezkz.casprzak.core.config.SimplificationSettings;

public class DefaultSimplificationSettings {
	// The settings below are the settings for `user`
	public static boolean simplifyFunctionsOfConstants;
	public static boolean simplifyFunctionsOfSpecialConstants;
	public static boolean distributeExponentsOverMultiplication;
	public static boolean addExponentsInProducts;
	public static boolean distributeMultiplicationOverAddition;
	public static boolean multiplyExponentsOfExponents;
	public static boolean executeTransformsOnSimplify;
	public static boolean enforceDomainAndRange;
	public static boolean simplifyLogAddition;
	public static boolean expandLogOfProducts;
	public static boolean extractLogExponents;
	public static boolean insertLogExponents;
	public static boolean doChangeOfBase;
	public static boolean doTrigIdentities;
	public static boolean trigComposition;

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
			false
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
				trigComposition
		);
	}

}
