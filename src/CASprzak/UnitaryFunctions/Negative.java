package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Negative extends UnitaryFunction{
	public Negative(Function function) {
		super(function);
	}

	public double evaluate(double[] variableValues) {
		return -1 * function.evaluate(variableValues);
	}
	public String toString() {
		return "-" + function.toString();
	}
}
