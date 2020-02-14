package CASprzak.CommutativeFunctions;

import CASprzak.Function;
import org.jetbrains.annotations.NotNull;

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
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" + ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) {
			toAdd[i] = functions[i].getDerivative(varID);
		}
		return new Add(toAdd);
	}

	public Function clone() {
		Function[] toAdd = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toAdd[i] = functions[i].clone();
		return new Add(toAdd);
	}

	public Function simplify() {
		return clone();
	}

	public int compareTo(@NotNull Function f) {
		return 0;
	}
}
