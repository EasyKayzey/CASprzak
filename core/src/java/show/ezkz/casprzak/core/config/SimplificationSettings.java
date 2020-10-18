package show.ezkz.casprzak.core.config;

import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.tools.exceptions.IncompatibleSettingsException;

/**
 * The {@link SimplificationSettings} object contains all of the simplification parameters to be used when a function tree is simplified.
 * It is passed into methods such as {@link show.ezkz.casprzak.core.functions.GeneralFunction#simplify(SimplificationSettings)}.
 * {@link show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings} contains often-used examples of simplification parameters.
 */
public class SimplificationSettings {

	/**
	 * Denotes whether functions of constants should be simplified. Ex: {@code 2*3^2 -> 18}
	 */
	public final boolean simplifyFunctionsOfConstants;

	/**
	 * Denotes whether or not special {@code Constant}s get simplified into decimal under addition, multiplication, or exponentiation. Ex: {@code \pi^2 -> 9.87}
	 */
	public final boolean simplifyFunctionsOfSpecialConstants;

	/**
	 * Denotes whether exponents should be distributed over multiplication in {@code simplify(settings)}. Ex: {@code (2x)^2 -> 4x^2}
	 */
	public final boolean distributeExponentsOverMultiplication;

	/**
	 * Denotes whether like terms in a product should have their exponents added. Ex: {@code x*x^2 -> x^3}
	 */
	public final boolean addExponentsInProducts;

	/**
	 * Denotes whether integer powers should be unwrapped into products. Ex {@code (x+1)^3 -> (x+1)*(x+1)*(x+1)}
	 */
	public final boolean unwrapIntegerPowers;

	/**
	 * Denotes whether products are distributed over addition in {@code simplify(settings)}. Ex: {@code x(y+z) -> (xy+xz)}
	 */
	public final boolean distributeMultiplicationOverAddition;

	/**
	 * Denotes whether nested powers should have their exponents multiplied. Ex: {@code (x^3)^2 -> x^6}
	 */
	public final boolean multiplyExponentsOfExponents;

	/**
	 * Denotes whether a {@link show.ezkz.casprzak.core.functions.unitary.transforms.Transformation} should execute its action when {@code simplify(settings)} is called. Ex: {@code d/dx[x^2] -> 2x} (without explicit execution)
	 */
	public final boolean executeTransformsOnSimplify;

	/**
	 * Denotes whether or not inverse simplifications conserve domain and range. Ex: {@code asin(sin(7*\pi/2)) -> -\pi/2} (without evaluation)
	 */
	public final boolean enforceDomainAndRange;

	/**
	 * Denotes whether sums of logs should become products of their arguments. Ex: {@code ln(x) + ln(y) -> ln(x*y)}
	 */
	public final boolean simplifyLogAddition;

	/**
	 * Denotes whether products inside logs should become sums of logs. Ex: {@code ln(x*y) -> ln(x) + ln(y)}
	 */
	public final boolean expandLogOfProducts;

	/**
	 * Denotes whether exponents inside of logs are pulled out. Ex. {@code ln(x^x) -> xln(x)}
	 */
	public final boolean extractLogExponents;

	/**
	 * Denotes whether or not {@link show.ezkz.casprzak.core.tools.algebra.LogSimplify#logChainRule(SimplificationSettings, Product)} is performed. Ex: {@code logb_a(b) * logb_b(c) * logb_x(y) / logb_x(z) -> logb_a(c) * logb_z(y)}
	 */
	public final boolean doChangeOfBase;

	/**
	 * Denotes whether trig identities are performed. Ex. {@code sin^2(x) + cos^2(x) -> 1 }
	 */
	public final boolean doTrigIdentities;

	/**
	 * Denotes whether trig composition simplify to algebraic expressions. Ex. {@code sin(arccos(x)) -> \sqrt(1-x^2)}
	 */
	public final boolean trigComposition;

	/**
	 * Denotes whether inverse should cancel. Ex {@code ln(e^x) -> x}
	 */
	public final boolean simplifyInverses;


	/**
	 * Constructs a class containing a set of simplification settings to be passed to simplification operations
	 */
	public SimplificationSettings(boolean simplifyFunctionsOfConstants,
								  boolean simplifyFunctionsOfSpecialConstants,
								  boolean distributeExponentsOverMultiplication,
								  boolean addExponentsInProducts,
								  boolean unwrapIntegerPowers,
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
		this.unwrapIntegerPowers = unwrapIntegerPowers;
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
		if (addExponentsInProducts && unwrapIntegerPowers)
			throw new IncompatibleSettingsException("addExponentsInProducts", "true", "unwrapIntegerPowers", "true");
	}

}
