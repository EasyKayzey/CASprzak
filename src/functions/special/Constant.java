package functions.special;

import functions.Function;
import org.jetbrains.annotations.Nullable;
import tools.DefaultFunctions;

import java.util.HashMap;
import java.util.Map;


public class Constant extends SpecialFunction {
	private static final HashMap<String, Double> specialConstants = new HashMap<>() {
		{
			put("pi", Math.PI);
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
	 * Constructs a new Constant from the specified numerical value
	 * @param constant The numerical value of the constant
	 */
	public Constant(double constant) {
		this.constant = constant;
		this.constantKey = null;
	}

	/**
	 * Constructs a new special Constant from its String
	 * @param constantString The string of the special Constant
	 */
	@SuppressWarnings("NullableProblems")
	public Constant(String constantString) {
		if (!isSpecialConstant(constantString))
			throw new IllegalArgumentException(constantString + " is not a special constant.");
		constantKey = constantString;
		constant = specialConstants.get(constantKey);
	}

	/**
	 * Returns true if string is a special Constant
	 * @param string The string that is being checked if it is a special constant
	 * @return true if string is a special Constant
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
	 */
	public static double addSpecialConstant(String string, double value) {
		specialConstants.put(string, value);
		return value;
	}

	/**
	 * Removes a special constant
	 * @param string name of constant
	 */
	public static double removeSpecialConstant(String string) {
		return specialConstants.remove(string);
	}

	public double evaluate(Map<Character, Double> variableValues) {
		return constant;
	}


	public String toString() {
		if (constantKey != null)
			return constantKey;
		return String.valueOf(constant);
	}

	public Function getDerivative(char varID) {
		return new Constant(0);
	}

	public Function clone() {
		if (constantKey == null)
			return new Constant(constant);
		else return new Constant(constantKey);
	}

	public Function simplify() {
		if (constant == Math.PI)
			return DefaultFunctions.PI;
		else if (constant == Math.E)
			return DefaultFunctions.E;
		else
			return this;
	}


	public Function substitute(char varID, Function toReplace) {
		return this;
	}


	public boolean equals(Function that) {
		return (that instanceof Constant) && (constant == ((Constant) that).constant);
	}

	public int compareSelf(Function that) {
		if (constantKey != null) {
			if (((Constant) that).constantKey != null)
				//noinspection ConstantConditions
				return this.constantKey.compareTo(((Constant) that).constantKey);
			return -1;
		}
		//noinspection VariableNotUsedInsideIf
		if (((Constant) that).constantKey != null)
			return 1;
		return (int) Math.signum(this.constant - ((Constant) that).constant);
	}
}
