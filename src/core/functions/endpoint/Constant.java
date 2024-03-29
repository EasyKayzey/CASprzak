package core.functions.endpoint;

import core.config.Settings;
import core.functions.GeneralFunction;
import core.tools.ParsingTools;
import core.tools.exceptions.IllegalNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;


public class Constant extends EndpointFunction {

	/**
	 * The default values in {@link #specialConstants}, which are reset every time {@link #resetConstants()} is called
 	 */
	public static final Map<String, Double> defaultSpecialConstants = new HashMap<>() {
		{
			put("π", Math.PI);
			put("e", Math.E);
		}
	};

	/**
	 * Strings with a pre-set value such as {@code π} and {@code e}
	 */
	public static Map<String, Double> specialConstants = new HashMap<>(defaultSpecialConstants);


	/**
	 * The numerical value of the constant
	 */
	public final double constant;

	/**
	 * The String relating to this special constant (null if normal constant)
	 */
	public final String constantKey;

	/**
	 * Constructs a new {@link Constant} from the specified numerical value
	 * @param constant The numerical value of the constant
	 */
	public Constant(double constant) {
		this.constant = constant;
		this.constantKey = null;
	}

	/**
	 * Constructs a new special {@link Constant} from its String
	 * @param constantKey The String of the special {@link Constant}
	 */
	@SuppressWarnings("NullableProblems")
	public Constant(String constantKey) {
		if (!isSpecialConstant(constantKey))
			throw new IllegalArgumentException(constantKey + " is not a special constant.");
		this.constantKey = constantKey;
		constant = specialConstants.get(constantKey);
	}

	/**
	 * Returns true if string is a special {@link Constant}
	 * @param constantString The string that is being checked if it is a special constant
	 * @return true if string is a special {@link Constant}
	 */
	public static boolean isSpecialConstant(String constantString) {
		return specialConstants.containsKey(constantString);
	}

	/**
	 * Returns the value of a special constant
	 * @param constantString name of constant
	 * @return the value of the constant
	 * @throws IllegalArgumentException if the constant is not a special constant
	 */
	public static double getSpecialConstant(String constantString) {
		if (specialConstants.containsKey(constantString))
			return specialConstants.get(constantString);
		else
			throw new IllegalArgumentException(constantString + " is not a defined special constant.");
	}

	/**
	 * Defines a new special constant
	 * @param string name of constant
	 * @param value value of constant
	 * @return the value of the constant for convenience
	 */
	public static double addSpecialConstant(String string, double value) {
		if (Settings.enforcePatternMatchingNames && !ParsingTools.validNames.matcher(string).matches())
			throw new IllegalNameException(string);
		specialConstants.put(string, value);
		return value;
	}

	/**
	 * Removes a special constant
	 * @param string name of constant
	 * @return the value of the constant
	 */
	public static double removeSpecialConstant(String string) {
		return specialConstants.remove(string);
	}

	public double evaluate(Map<String, Double> variableValues) {
		return constant;
	}

	/**
	 * Returns true if the Constant is a special {@link Constant}
	 * @return true if the Constant is a special {@link Constant}
	 */
	public boolean isSpecial() {
		return constantKey != null;
	}


	public String toString() {
		if (isSpecial())
			return ParsingTools.processEscapes(constantKey);
		else if (Settings.truncateNearIntegers && ParsingTools.isAlmostInteger(constant) && Math.abs(constant) > 0.5)
				return String.valueOf(ParsingTools.toInteger(constant));
		else
			return String.valueOf(constant);
	}

	public GeneralFunction getDerivative(String varID) {
		return new Constant(0);
	}

	public GeneralFunction clone() {
		if (!isSpecial())
			return new Constant(constant);
		else
			return new Constant(constantKey);
	}

	public GeneralFunction simplify() {
		if (!isSpecial())
			for (Map.Entry<String, Double> entry : specialConstants.entrySet())
				if (Math.abs(constant - entry.getValue()) < Settings.equalsMargin)
					return new Constant(entry.getKey());
		return this;
	}


	public GeneralFunction substituteAll(Predicate<? super GeneralFunction> test, Function<? super GeneralFunction, ? extends GeneralFunction> replacer) {
		return this;
	}


	public boolean equalsFunction(GeneralFunction that) {
		return (that instanceof Constant) && (Math.abs(constant - ((Constant) that).constant) < Settings.equalsMargin);
	}

	/**
	 * Resets {@link #specialConstants} to {@link #defaultSpecialConstants}
	 */
	public static void resetConstants() {
		specialConstants.clear();
		specialConstants = new HashMap<>(defaultSpecialConstants);
	}


	@SuppressWarnings({"ConstantConditions"})
	public int compareSelf(GeneralFunction that) {
		if (isSpecial() && ((Constant) that).isSpecial())
			return this.constantKey.compareTo(((Constant) that).constantKey);
		else if (isSpecial())
			return 1;
		else if (((Constant) that).isSpecial())
			return -1;
		else
			return (int) Math.signum(this.constant - ((Constant) that).constant);
	}

	public int hashCode() {
		return Double.hashCode(constant);
	}
}
