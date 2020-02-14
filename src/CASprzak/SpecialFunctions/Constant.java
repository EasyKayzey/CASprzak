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

	@Override
	public Function derivative() {
		return new Constant(0);
	}
}
