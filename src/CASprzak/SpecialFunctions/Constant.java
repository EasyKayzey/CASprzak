package CASprzak.SpecialFunctions;

import CASprzak.Function;

public class Constant extends Function {
	protected double constant;
	protected int constantID;

	private static String[] specialConstantStrings = {"pi", "e"};
	private static double[] specialConstants = {Math.PI, Math.E};

	public Constant(double constant) {
		this.constant = constant;
		this.constantID = -1;
	}

	public Constant(String constantString) {
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

	public double evaluate(double[] variableValues) {
		return constant;
	}

	public String toString() {
		return "" + constant;
	}

	public Function getDerivative(int varID) {
		return new Constant(0);
	}
}
