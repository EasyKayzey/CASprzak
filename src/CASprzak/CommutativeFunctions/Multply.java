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
		StringBuilder temp = new StringBuilder();
		for (Function i : functions) {
			temp.append(i.toString() + " * ");
		}
		return temp.toString();
	}

	@Override
	public Function derivative(int varID) {
		return  new Add(new Multply(functions[0], functions[1].derivative(varID)), new Multply(functions[0].derivative(varID), functions[1]));
	}
}
