package CASprzak.CommutativeFunctions;

import CASprzak.Function;

public class Add extends CommutativeFunction{
	public Add(Function[] functions) {
		super(functions);
	}

	public Add(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 0;
		for (Function f : functions) accumulator += f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		return "";
	}
}
