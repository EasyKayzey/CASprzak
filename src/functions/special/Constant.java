package functions.special;

import config.Settings;
import functions.GeneralFunction;
import org.jetbrains.annotations.Nullable;
import tools.ParsingTools;
import tools.exceptions.IllegalNameException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;


public class Constant extends SpecialFunction {
	/**
	 * Symbols or Strings with a dedicated value. The defaults are {@code π} and {@code e}.
	 */
	public static final HashMap<String, Double> specialConstants = new HashMap<>() {
		{
			put("π", Math.PI);
			put("e", Math.E);
		}
	};

	/**
	 * The numerical value of the constant
	 */
	public final double constant;
	/**
	 * The string relating to this special constant (null if normal constant)
	 */
	public final @Nullable String constantKey;

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
	 * @param constantKey The string of the special {@link Constant}
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
	 * @param string The string that is being checked if it is a special constant
	 * @return true if string is a special {@link Constant}
	 */
	public static boolean isSpecialConstant(String string) {
		return specialConstants.containsKey(string);
	}

	/**
	 * Returns the value of a special constant
	 * @param string name of constant
	 * @return the value of the constant
	 */
	public static double getSpecialConstant(String string) {
		return specialConstants.get(string);
	}

	/**
	 * Defines a new special constant
	 * @param string name of constant
	 * @param value value of constant
	 * @return the value of the constant for convenience
	 */
	public static double addSpecialConstant(String string, double value) {
		if (!Variable.validVariables.matcher(string).matches())
			throw new IllegalNameException(string);
		string = ParsingTools.processEscapes(string);
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


	public String toString() {
		if (constantKey != null)
			return constantKey;
		else
			return String.valueOf(constant);
	}

	public GeneralFunction getDerivative(String varID) {
		return new Constant(0);
	}

	public GeneralFunction clone() {
		if (constantKey == null)
			return new Constant(constant);
		else
			return new Constant(constantKey);
	}

	public GeneralFunction simplify() {
		if (constantKey == null)
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

	public static void resetConstants() {
		specialConstants.clear();
		specialConstants.put("π", Math.PI);
		specialConstants.put("e", Math.E);
	}


	@SuppressWarnings({"VariableNotUsedInsideIf", "ConstantConditions"})
	public int compareSelf(GeneralFunction that) {
		if (constantKey != null && ((Constant) that).constantKey != null)
			return this.constantKey.compareTo(((Constant) that).constantKey);
		else if (constantKey != null) // && ((Constant) that).constantKey == null
			return 1;
		else if (((Constant) that).constantKey != null) // && constantKey == null
			return -1;
		else
			return (int) Math.signum(this.constant - ((Constant) that).constant);
	}

	public int hashCode() {
		return Double.hashCode(constant);
	}
}
