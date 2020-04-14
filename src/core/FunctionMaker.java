package core;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.*;

public class FunctionMaker {

	/**
	 * Returns a new  {@link Constant}
	 * @param constant value of constant
	 * @return new {@link Constant}
	 */
	public Function constant(double constant) {
		return new Constant(constant);
	}

	/**
	 * Returns a new special {@link Constant} like "e" or "pi"
	 * @param constantString string of constant
	 * @return new {@link Constant}
	 */
	public Function specialConstant(String constantString) {
		return new Constant(constantString);
	}

	/**
	 * Returns a new {@link Variable} with a varID and includes the variable names
	 * @param varID ID of variable
	 * @param varNames array of variable names
	 * @return new {@link Variable}
	 */
	public Function variable(int varID, char... varNames) {
		return new Variable(varID, varNames);
	}

	/**
	 * Returns a {@link Function} corresponding to a "unitary" operation string
	 * @param functionName the string of the operation (e.g. "-" or "csc")
	 * @param function the {@link Function} to be operated on
	 * @return new {@link Function}
	 */
	public Function makeUnitary(String functionName, Function function) {
		switch (functionName) {
			case "-": return new Multiply(new Constant(-1), function);
			case "/": return new Pow(new Constant(-1), function);
			case "sqrt": return new Pow(new Constant(.5), function);
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
			default: throw new IllegalArgumentException("Invalid functionName " + functionName);
		}
	}

	/**
	 * Returns a {@link Function} corresponding to a "binary" operation string
	 * NOTE: The functions are sometimes in a weird order for non-commutative types, so always check the constructors
	 * @param functionName the string of the operation (e.g. "*" or "logb")
	 * @param function1 one {@link Function} to be operated on
	 * @param function2 another {@link Function} to be operated on
	 * @return new {@link Function}
	 */
	public Function makeBinary(String functionName, Function function1, Function function2) {
		switch (functionName) {
			case "+": return new Add(function1, function2);
			case "*": return new Multiply(function1, function2);
			case "^": return new Pow(function1, function2);
			case "logb": return new Logb(function1, function2);
			default: throw new IllegalArgumentException("Invalid functionName " + functionName);
		}
	}
}
