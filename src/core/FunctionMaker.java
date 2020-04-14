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
		return switch (functionName) {
			case "-" -> new Multiply(new Constant(-1), function);
			case "/" -> new Pow(new Constant(-1), function);
			case "sqrt" -> new Pow(new Constant(.5), function);
			case "sin" -> new Sin(function);
			case "cos" -> new Cos(function);
			case "tan" -> new Tan(function);
			case "csc" -> new Csc(function);
			case "sec" -> new Sec(function);
			case "cot" -> new Cot(function);
			case "asin" -> new Asin(function);
			case "acos" -> new Acos(function);
			case "atan" -> new Atan(function);
			case "sinh" -> new Sinh(function);
			case "cosh" -> new Cosh(function);
			case "tanh" -> new Tanh(function);
			case "ln" -> new Ln(function);
			default -> throw new IllegalArgumentException("Invalid functionName " + functionName);
		};
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
		return switch (functionName) {
			case "+" -> new Add(function1, function2);
			case "*" -> new Multiply(function1, function2);
			case "^" -> new Pow(function1, function2);
			case "logb" -> new Logb(function1, function2);
			default -> throw new IllegalArgumentException("Invalid functionName " + functionName);
		};
	}
}
