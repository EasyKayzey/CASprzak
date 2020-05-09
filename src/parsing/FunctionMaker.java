package parsing;

import functions.GeneralFunction;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Variable;
import functions.unitary.combo.Factorial;
import functions.unitary.piecewise.Abs;
import functions.unitary.piecewise.Dirac;
import functions.unitary.piecewise.Sign;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
import functions.unitary.transforms.Differential;
import functions.unitary.transforms.Integral;
import functions.unitary.transforms.PartialDerivative;
import functions.unitary.trig.inverse.*;
import functions.unitary.trig.normal.*;
import tools.DefaultFunctions;

public class FunctionMaker {

	private FunctionMaker(){}

	/**
	 * Returns a {@link GeneralFunction} corresponding to a "unitary" operation string
	 * @param functionName the string of the operation (e.g. "-" or "csc")
	 * @param function     the {@link GeneralFunction} to be operated on
	 * @return new {@link GeneralFunction}
	 */
	public static GeneralFunction makeUnitary(String functionName, GeneralFunction function) {
		return switch (functionName) {
			case "-" 			-> new Product(DefaultFunctions.NEGATIVE_ONE, function);
			case "/" 			-> new Pow(DefaultFunctions.NEGATIVE_ONE, function);
			case "!" 			-> Factorial.defaultFactorial(function); //TODO implement integral and d/dx here
			case "\\ln" 		-> new Ln(function);
			case "\\log"		-> new Logb(function, DefaultFunctions.TEN);
			case "\\exp" 		-> new Exp(function);
			case "\\abs" 		-> new Abs(function);
			case "\\difn"		-> new Differential(function, ((Variable) function).varID);
			case "\\sqrt" 		-> new Pow(DefaultFunctions.HALF, function);
			case "\\sign" 		-> new Sign(function);
			case "\\dirac" 		-> new Dirac(function);
			case "\\sin" 		-> new Sin(function);
			case "\\cos" 		-> new Cos(function);
			case "\\tan" 		-> new Tan(function);
			case "\\csc" 		-> new Csc(function);
			case "\\sec" 		-> new Sec(function);
			case "\\cot" 		-> new Cot(function);
			case "\\asin" 		-> new Asin(function);
			case "\\acos" 		-> new Acos(function);
			case "\\atan" 		-> new Atan(function);
			case "\\acsc" 		-> new Acsc(function);
			case "\\asec" 		-> new Asec(function);
			case "\\acot" 		-> new Acot(function);
			case "\\sinh" 		-> new Sinh(function);
			case "\\cosh" 		-> new Cosh(function);
			case "\\tanh" 		-> new Tanh(function);
			case "\\csch" 		-> new Csch(function);
			case "\\sech" 		-> new Sech(function);
			case "\\coth" 		-> new Coth(function);
			case "\\asinh" 		-> new Asinh(function);
			case "\\acosh" 		-> new Acosh(function);
			case "\\atanh" 		-> new Atanh(function);
			case "\\acsch" 		-> new Acsch(function);
			case "\\asech" 		-> new Asech(function);
			case "\\acoth" 		-> new Acoth(function);
			default 			-> throw new UnsupportedOperationException("Invalid functionName " + functionName);
		};
	}

	/**
	 * Returns a {@link GeneralFunction} corresponding to a "binary" operation string
	 * NOTE: The functions are sometimes in a weird order for non-commutative types, so always check the constructors
	 * @param functionName the string of the operation (e.g. "*" or "logb")
	 * @param first     another {@link GeneralFunction} to be operated on
	 * @param second    one {@link GeneralFunction} to be operated on
	 * @return new {@link GeneralFunction}
	 */
	public static GeneralFunction makeBinary(String functionName, GeneralFunction first, GeneralFunction second) {
		return switch (functionName) {
			case "+" 		-> new Sum(first, second);
			case "*" 		-> new Product(first, second);
			case "^" 		-> new Pow(second, first);
			case "C" 		-> new Product(Factorial.defaultFactorial(first), new Pow(DefaultFunctions.NEGATIVE_ONE, new Product(Factorial.defaultFactorial(second), Factorial.defaultFactorial(new Sum(first, new Product(DefaultFunctions.NEGATIVE_ONE, second))))));
			case "P" 		-> new Product(Factorial.defaultFactorial(first), new Pow(DefaultFunctions.NEGATIVE_ONE, Factorial.defaultFactorial(new Sum(first, new Product(DefaultFunctions.NEGATIVE_ONE, second)))));
			case "\\pd"		-> new PartialDerivative(second, ((Variable) first).varID);
			case "\\int" 	-> new Integral(first, ((Differential) second).getRespectTo());
			case "\\logb" 	-> new Logb(second, first);
			case "\\frac" 	-> new Product(first, DefaultFunctions.reciprocal(second));
			default 		-> throw new UnsupportedOperationException("Invalid functionName " + functionName);
		};
	}
}
