package CASprzak.SpecialFunctions;

import CASprzak.Function;

public class Constant extends Function {
	private double constant;

	public Constant(double constant) {
		this.constant = constant;
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
