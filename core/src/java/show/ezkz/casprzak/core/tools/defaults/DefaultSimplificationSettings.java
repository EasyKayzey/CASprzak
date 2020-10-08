package show.ezkz.casprzak.core.tools.defaults;

import show.ezkz.casprzak.core.config.SimplificationSettings;

public class DefaultSimplificationSettings {
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
	public static SimplificationSettings minimal;
	public static SimplificationSettings auto;
	public static SimplificationSettings aggressive;

	private DefaultSimplificationSettings() {}

	public void generateAll() {

	}

	public void generateUser() {

	}

}
