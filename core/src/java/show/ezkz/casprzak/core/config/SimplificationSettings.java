package show.ezkz.casprzak.core.config;

import show.ezkz.casprzak.core.tools.exceptions.IncompatibleSettingsException;

/**
 * The {@link SimplificationSettings} object contains all of the simplification parameters to be used when a function tree is simplified.
 * It is passed into methods such as {@link show.ezkz.casprzak.core.functions.GeneralFunction#simplify(SimplificationSettings)}.
 * {@link show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings} contains often-used examples of simplification parameters.
 */
public class SimplificationSettings {
	public final boolean simplifyFunctionsOfConstants;
	public final boolean simplifyFunctionsOfSpecialConstants;
	public final boolean distributeExponentsOverMultiplication;
	public final boolean addExponentsInProducts;
	public final boolean distributeMultiplicationOverAddition;
	public final boolean multiplyExponentsOfExponents;
	public final boolean executeTransformsOnSimplify;
	public final boolean enforceDomainAndRange;
	public final boolean simplifyLogAddition;
	public final boolean expandLogOfProducts;
	public final boolean extractLogExponents;
	public final boolean doChangeOfBase;
	public final boolean doTrigIdentities;
	public final boolean trigComposition;
	public final boolean simplifyInverses;

	public SimplificationSettings(boolean simplifyFunctionsOfConstants,
								  boolean simplifyFunctionsOfSpecialConstants,
								  boolean distributeExponentsOverMultiplication,
								  boolean addExponentsInProducts,
								  boolean distributeMultiplicationOverAddition,
								  boolean multiplyExponentsOfExponents,
								  boolean executeTransformsOnSimplify,
								  boolean enforceDomainAndRange,
								  boolean simplifyLogAddition,
								  boolean expandLogOfProducts,
								  boolean extractLogExponents,
								  boolean doChangeOfBase,
								  boolean doTrigIdentities,
								  boolean trigComposition,
								  boolean simplifyInverses) {
		this.simplifyFunctionsOfConstants = simplifyFunctionsOfConstants;
		this.simplifyFunctionsOfSpecialConstants = simplifyFunctionsOfSpecialConstants;
		this.distributeExponentsOverMultiplication = distributeExponentsOverMultiplication;
		this.addExponentsInProducts = addExponentsInProducts;
		this.distributeMultiplicationOverAddition = distributeMultiplicationOverAddition;
		this.multiplyExponentsOfExponents = multiplyExponentsOfExponents;
		this.executeTransformsOnSimplify = executeTransformsOnSimplify;
		this.enforceDomainAndRange = enforceDomainAndRange;
		this.simplifyLogAddition = simplifyLogAddition;
		this.expandLogOfProducts = expandLogOfProducts;
		this.extractLogExponents = extractLogExponents;
		this.doChangeOfBase = doChangeOfBase;
		this.doTrigIdentities = doTrigIdentities;
		this.trigComposition = trigComposition;
		this.simplifyInverses = simplifyInverses;
		assertValidity();
	}

	private void assertValidity() {
		if (simplifyLogAddition && expandLogOfProducts)
			throw new IncompatibleSettingsException("simplifyLogAddition", "true", "expandLogOfProducts", "true");
	}
}
