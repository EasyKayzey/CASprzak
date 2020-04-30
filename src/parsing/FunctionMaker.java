package parsing;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.Abs;
import functions.unitary.Dirac;
import functions.unitary.Ln;
import functions.unitary.Sign;
import functions.unitary.combo.Factorial;
import functions.unitary.trig.*;

public class FunctionMaker {

	private FunctionMaker(){}

	/**
	 * Returns a new  {@link Constant}
	 * @param constant value of constant
	 * @return new {@link Constant}
	 */
	public static Function constant(double constant) {
		return new Constant(constant);
	}

	/**
	 * Returns a new special {@link Constant} like "e" or "pi"
	 * @param constantString string of constant
	 * @return new {@link Constant}
	 */
	public static Function specialConstant(String constantString) {
		return new Constant(constantString);
	}

	/**
	 * Returns a new {@link Variable} with a varID
	 * @param varID    ID of variable
	 * @return new {@link Variable}
	 */
	public static Function variable(char varID) {
		return new Variable(varID);
	}

	/**
	 * Returns a {@link Function} corresponding to a "unitary" operation string
	 * @param functionName the string of the operation (e.g. "-" or "csc")
	 * @param function     the {@link Function} to be operated on
	 * @return new {@link Function}
	 */
	public static Function makeUnitary(String functionName, Function function) {
		return switch (functionName) {
			case "-" -> new Product(new Constant(-1), function);
			case "/" -> new Pow(new Constant(-1), function);
			case "!" -> Factorial.defaultFactorial(function);
			case "dirac" -> new Dirac(function);
			case "sqrt" -> new Pow(new Constant(.5), function);
			case "sign" -> new Sign(function);
			case "abs" -> new Abs(function);
			case "ln" -> new Ln(function);
			case "sin" -> new Sin(function);
			case "cos" -> new Cos(function);
			case "tan" -> new Tan(function);
			case "csc" -> new Csc(function);
			case "sec" -> new Sec(function);
			case "cot" -> new Cot(function);
			case "asin" -> new Asin(function);
			case "acos" -> new Acos(function);
			case "atan" -> new Atan(function);
			case "acsc" -> new Acsc(function);
			case "asec" -> new Asec(function);
			case "acot" -> new Acot(function);
			case "sinh" -> new Sinh(function);
			case "cosh" -> new Cosh(function);
			case "tanh" -> new Tanh(function);
			case "csch" -> new Csch(function);
			case "sech" -> new Sech(function);
			case "coth" -> new Coth(function);
			case "asinh" -> new Asinh(function);
			case "acosh" -> new Acosh(function);
			case "atanh" -> new Atanh(function);
			case "acsch" -> new Acsch(function);
			case "asech" -> new Asech(function);
			case "acoth" -> new Acoth(function);
			default -> throw new UnsupportedOperationException("Invalid functionName " + functionName);
		};
	}

	/**
	 * Returns a {@link Function} corresponding to a "binary" operation string
	 * NOTE: The functions are sometimes in a weird order for non-commutative types, so always check the constructors
	 * @param functionName the string of the operation (e.g. "*" or "logb")
	 * @param second    one {@link Function} to be operated on
	 * @param first     another {@link Function} to be operated on
	 * @return new {@link Function}
	 */
	public static Function makeBinary(String functionName, Function second, Function first) {
		return switch (functionName) {
			case "+" -> new Sum(second, first);
			case "*" -> new Product(second, first);
			case "^" -> new Pow(second, first);
			case "logb" -> new Logb(second, first);
			case "C" -> new Product(Factorial.defaultFactorial(first), new Pow(Constant.NEGATIVE_ONE, new Product(Factorial.defaultFactorial(second), Factorial.defaultFactorial(new Sum(first, new Product(Constant.NEGATIVE_ONE, second))))));
			case "P" -> new Product(Factorial.defaultFactorial(first), new Pow(Constant.NEGATIVE_ONE, Factorial.defaultFactorial(new Sum(first, new Product(Constant.NEGATIVE_ONE, second)))));
			default -> throw new UnsupportedOperationException("Invalid functionName " + functionName);
		};
	}
}
