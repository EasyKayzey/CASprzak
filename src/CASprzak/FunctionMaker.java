package CASprzak;

import CASprzak.UnitaryFunctions.*;
import CASprzak.BinaryFunctions.*;
import CASprzak.CommutativeFunctions.*;
import CASprzak.SpecialFunctions.*;

public class FunctionMaker {
	public Function constant(double constant) {
		return new Constant(constant);
	}
	public Function specialConstant(String constantString) {
		return new Constant(constantString);
	}

	public Function variable(int varID, char[] varNames) {
		return new Variable(varID, varNames);
	}

	public Function find1(String functionName, Function function) {
		switch (functionName) {
			case "sin": return new Sin(function);
			case "cos": return new Cos(function);
			case "tan": return new Tan(function);
			case "csc": return new Csc(function);
			case "sec": return new Sec(function);
			case "cot": return new Cot(function);
			case "asin": return new Asin(function);
			case "acos": return new Acos(function);
			case "atan": return new Atan(function);
			case "sinh": return new Sinh(function);
			case "cosh": return new Cosh(function);
			case "tanh": return new Tanh(function);
			case "ln": return new Ln(function);
			case "negative": return new Negative(function);
			case "reciprocal": return new Reciprocal(function);
			default: throw new IllegalArgumentException("Invalid functionName " + functionName);
		}
	}

	public Function find2(String functionName, Function function1, Function function2) {
		switch (functionName) {
			case "+": return new Add(function1, function2);
			case "-": return new Add(function2, new Negative(function1));
			case "*": return new Multiply(function1, function2);
			case "/": return new Multiply(function2, new Reciprocal(function1));
			case "^": return new Pow(function1, function2);
			case "logb": return new Logb(function1, function2);
			default: throw new IllegalArgumentException("Invalid functionName " + functionName);
		}
	}
}
