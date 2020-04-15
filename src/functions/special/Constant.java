package functions.special;

import functions.Function;

public class Constant extends Function {
	private static final String[] specialConstantStrings = {"pi", "e"};
	private static final double[] specialConstants = {Math.PI, Math.E};
	public final double constant;
	public final int constantID;

	public Constant(double constant) {
		this.constant = constant;
		this.constantID = -1;
	}

	public Constant(int constantID, boolean isSpecialConstant) {
		if (!isSpecialConstant)
			throw new IllegalArgumentException("This should never happen in Constant constructor");
		this.constantID = constantID;
		constant = specialConstants[constantID];
	}

	public Constant(String constantString) {
		if (!isSpecialConstant(constantString))
			throw new IllegalArgumentException(constantString + " is not a special constant.");
		constantID = getSpecialConstantID(constantString);
		constant = specialConstants[constantID];
	}

	public static boolean isSpecialConstant(String s) {
		for (String specialConstantString : specialConstantStrings) {
			if (specialConstantString.equals(s)) return true;
		}
		return false;
	}

	public static int getSpecialConstantID(String s) {
		for (int i = 0; i < specialConstantStrings.length; i++) {
			if (specialConstantStrings[i].equals(s)) return i;
		}
		return -1;
	}

	public double evaluate(double... variableValues) {
		return constant;
	}

	/**
	 * Returns a String representation of the Function
	 *
	 * @return String representation of function
	 */
	public String toString() {
		if (constantID != -1)
			return specialConstantStrings[constantID];
		return String.valueOf(constant);
	}

	public Function getDerivative(int varID) {
		return new Constant(0);
	}

	public Function clone() {
		if (constantID == -1) return new Constant(constant);
		else return new Constant(constantID, true);
	}

	public Function simplify() {
		return this;
	}


	public Function substitute(int varID, Function toReplace) {
		return this;
	}


	public boolean equals(Function that) {
		return (that instanceof Constant) && (constant == ((Constant) that).constant);
	}

	public int compareSelf(Function that) {
		if (constantID != -1) {
			if (((Constant) that).constantID != -1)
				return this.constantID - ((Constant) that).constantID;
			return -1;
		}
		if (((Constant) that).constantID != -1)
			return 1;
		return (int) Math.signum(this.constant - ((Constant) that).constant);
	}
}
