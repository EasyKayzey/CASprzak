package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Reciprocal extends UnitaryFunction{
	public Reciprocal(Function function) {
		super(function);
	}

	public double evaluate(double[] variableValues) {
		return 1 / function.evaluate(variableValues);
	}
	public String toString() {
		return "("+ function.toString()+")^(-1)";
	}
}
