package show.ezkz.casprzak.core.config;

import show.ezkz.casprzak.core.tools.exceptions.IncompatibleSettingsException;

// TODO document
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
	public final boolean insertLogExponents;
	public final boolean doChangeOfBase;
	public final boolean doTrigIdentities;
	public final boolean trigComposition;

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
								  boolean insertLogExponents,
								  boolean doChangeOfBase,
								  boolean doTrigIdentities,
								  boolean trigComposition) {
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
		this.insertLogExponents = insertLogExponents;
		this.doChangeOfBase = doChangeOfBase;
		this.doTrigIdentities = doTrigIdentities;
		this.trigComposition = trigComposition;
		assertValidity();
	}

	private void assertValidity() {
		if (simplifyLogAddition && expandLogOfProducts)
			throw new IncompatibleSettingsException("simplifyLogAddition", "true", "expandLogOfProducts", "true");
		if (extractLogExponents && insertLogExponents)
			throw new IncompatibleSettingsException("extractLogExponents", "true", "insertLogExponents", "true");
	}
}
