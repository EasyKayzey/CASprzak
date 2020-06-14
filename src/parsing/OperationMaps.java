package parsing;

import functions.binary.Logb;
import functions.binary.Pow;
import functions.binary.Rand;
import functions.binary.integer.IntegerQuotient;
import functions.binary.integer.Modulo;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.commutative.integer.GCD;
import functions.commutative.integer.LCM;
import functions.special.Variable;
import functions.unitary.integer.combo.Factorial;
import functions.unitary.piecewise.*;
import functions.unitary.specialcases.Exp;
import functions.unitary.specialcases.Ln;
import functions.unitary.transforms.Differential;
import functions.unitary.transforms.Integral;
import functions.unitary.transforms.PartialDerivative;
import functions.unitary.trig.inverse.*;
import functions.unitary.trig.normal.*;
import tools.DefaultFunctions;
import tools.helperclasses.BinaryConstructor;
import tools.helperclasses.UnitaryConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link OperationMaps} is a centralized location for the strings of all unitary and binary operations to be stored for parsing.
 * The conversions between these strings and their {@link functions.GeneralFunction} equivalents are stored as lambdas in their respective maps.
 */
public class OperationMaps {

	private OperationMaps(){}

	/**
	 * A map between unitary operation strings and their associated constructor lambdas
	 */
	public static final Map<String, UnitaryConstructor> unitaryOperations = new HashMap<>() {
		{
			put("!", 		Factorial::defaultFactorial);
			put("-", 		DefaultFunctions::negative);
			put("/", 		DefaultFunctions::reciprocal);
			put("\\ln", 	Ln::new);
			put("\\int",	Integral::new);
			put("\\log", 	DefaultFunctions::log10);
			put("\\exp", 	Exp::new);
			put("\\abs", 	Abs::new);
			put("\\difn",	Differential::new);
			put("\\sqrt", 	DefaultFunctions::sqrt);
			put("\\sign", 	Sign::new);
			put("\\ceil", 	Ceil::new);
			put("\\floor", 	Floor::new);
			put("\\round", 	Round::new);
			put("\\dirac", 	Dirac::new);
			put("\\sin", 	Sin::new);
			put("\\cos", 	Cos::new);
			put("\\tan", 	Tan::new);
			put("\\csc", 	Csc::new);
			put("\\sec", 	Sec::new);
			put("\\cot", 	Cot::new);
			put("\\asin", 	Asin::new);
			put("\\acos", 	Acos::new);
			put("\\atan", 	Atan::new);
			put("\\acsc", 	Acsc::new);
			put("\\asec", 	Asec::new);
			put("\\acot", 	Acot::new);
			put("\\arcsin", 	Asin::new);
			put("\\arccos", 	Acos::new);
			put("\\arctan", 	Atan::new);
			put("\\arccsc", 	Acsc::new);
			put("\\arcsec", 	Asec::new);
			put("\\arccot", 	Acot::new);
			put("\\sinh", 	Sinh::new);
			put("\\cosh", 	Cosh::new);
			put("\\tanh", 	Tanh::new);
			put("\\csch", 	Csch::new);
			put("\\sech", 	Sech::new);
			put("\\coth", 	Coth::new);
			put("\\asinh", 	Asinh::new);
			put("\\acosh", 	Acosh::new);
			put("\\atanh", 	Atanh::new);
			put("\\acsch", 	Acsch::new);
			put("\\asech", 	Asech::new);
			put("\\acoth", 	Acoth::new);
			put("\\arcsinh", 	Asinh::new);
			put("\\arccosh", 	Acosh::new);
			put("\\arctanh", 	Atanh::new);
			put("\\arccsch", 	Acsch::new);
			put("\\arcsech", 	Asech::new);
			put("\\arccoth", 	Acoth::new);
		}
	};

	/**
	 * A map between binary operation strings and their associated constructor lambdas
	 */
	public static final Map<String, BinaryConstructor> binaryOperations = new HashMap<>() {
		{
			put("+", 		Sum::new);
			put("*", 		Product::new);
			put("^", 		(first, second) -> new Pow(second, first));
			put("%", 		(first, second) -> new Modulo(second, first));
			put("//", 		(first, second) -> new IntegerQuotient(second, first));
			put("C", 		DefaultFunctions::choose);
			put("P", 		DefaultFunctions::permute);
			put("\\pd", 	(first, second) -> new PartialDerivative(second, ((Variable) first).varID));
			put("\\logb", 	(first, second) -> new Logb(second, first));
			put("\\frac", 	DefaultFunctions::frac);
			put("\\rand", 	(first, second) -> new Rand(second, first));
			put("\\gcd", 	GCD::new);
			put("\\lcm", 	LCM::new);
		}
	};
}
