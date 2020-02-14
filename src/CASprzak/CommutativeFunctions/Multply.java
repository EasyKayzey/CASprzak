package CASprzak.CommutativeFunctions;

import CASprzak.Function;

public class Multply extends CommutativeFunction{

	public Multply(Function[] functions) {
		super(functions);
	}

	public Multply(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 1;
		for (Function f : functions) accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		return "";
	}
}
