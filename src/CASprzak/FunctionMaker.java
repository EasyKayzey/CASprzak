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

	public Function find1(String i, Function c) {
		switch (i) {
			case "sin": return new Sin(c);
			case "cos": return new Cos(c);
			case "tan": return new Tan(c);
			case "csc": return new Csc(c);
			case "sec": return new Sec(c);
			case "cot": return new Cot(c);
			case "asin": return new Asin(c);
			case "acos": return new Acos(c);
			case "atan": return new Atan(c);
			case "sinh": return new Sinh(c);
			case "cosh": return new Cosh(c);
			case "tanh": return new Tanh(c);
			case "ln": return new Ln(c);
			case "negative": return new Negative(c);
			case "reciprocal": return new Reciprocal(c);

		}
		return c;
	}

	public Function find2(String i, Function a, Function b) throws Exception {
		switch (i) {
			case "+":
				return new Add(a, b);
			case "-":
				return new Add(b, new Negative(a));
			case "*":
				return new Multiply(a, b);
			case "/":
				return new Multiply(b, new Reciprocal(a));
			case "^":
				return new Pow(a, b);
			case "logb":
				return new Logb(a, b);
		}
		throw new Exception("Function " + i + " does not exist");
	}
}
