package CASprzak.BinaryFunctions;

import CASprzak.Function;

public class Pow extends BinaryFunction {

	public Pow(Function function1, Function function2) {
		super(function1, function2);
	}

	@Override
	public String toString() {
		return "(" + function2.toString() + ")^(" + function1.toString() + ")";
	}

	@Override
	public double evaluate(double[] variableValues) {
		return Math.pow(function2.evaluate(variableValues), function1.evaluate(variableValues));
	}
}
