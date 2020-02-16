package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import org.jetbrains.annotations.NotNull;

public class Multiply extends CommutativeFunction{

	public Multiply(Function[] functions) {
		super(functions);
	}

	public Multiply(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 1;
		for (Function f : functions) accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" * ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			Function[] toMultiply = new Function[functions.length];
			for (int j = 0; j < functions.length; j++) {
				if (j == i) toMultiply[j] = functions[j].getDerivative(varID);
				else toMultiply[j] = functions[j];
			}
			toAdd[i] = new Multiply(toMultiply);
		}

		return new Add(toAdd);
	}

	public Function clone() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].clone();
		return new Multiply(toMultiply);
	}

	public Function simplify() {
		if (functions.length == 1) return functions[0].simplify();

		for (Function function : functions) {
			if (function instanceof Constant) {
				if (((Constant) function).evaluate() == 0) {
					return new Constant(0);
				}
			}
		}
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).evaluate() == 1) {
					return (new Add(ArrLib.removeFunctionAt(functions, i))).simplify();
				}
			}
		}

		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Multiply(toMultiply);
	}

	public int compareTo(@NotNull Function f) {
		return 0;
	}
}
