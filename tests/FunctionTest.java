import functions.GeneralFunction;
import functions.unitary.trig.inverse.*;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.DefaultFunctions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {

	@Test void fxReturnsX() {
		GeneralFunction test = FunctionParser.parseInfix("x");
		assertEquals(2, test.evaluate(Map.of("x",2.0)));
	}

	@Test void basicFunctionsWithX() {
		GeneralFunction test = FunctionParser.parseInfix("x^x+2+5*x");
		assertEquals(44, test.evaluate(Map.of("x",3.0)));
	}

	@Test void multipleVariablesWorks() {
        GeneralFunction test = FunctionParser.parseInfix("x+y+y");
		assertEquals(11, test.evaluate(Map.of("x",3.0, "y", 4.0)));
	}

	@Test void logbWorks() {
		GeneralFunction test = FunctionParser.parseInfix("\\logb_3(x)");
		assertEquals(4, test.evaluate(Map.of("x",81.0)));
	}

	@Test void trigWorks() {
		GeneralFunction test = FunctionParser.parseInfix("(\\sin(x))^2+(\\cos x)^2");
		assertEquals(1, test.evaluate(Map.of("x",81.0)));
	}

	@Test void eToTheXWorks() {
		GeneralFunction test = FunctionParser.parseInfix("e^x");
		assertEquals(1, test.evaluate(Map.of("x",0.0)));
	}

	@Test void lnWorks() {
		GeneralFunction test = FunctionParser.parseInfix("\\ln(e)");
		assertEquals(1, test.evaluate(Map.of("x",1.5)));
	}

	@Test void tanWorks() {
		GeneralFunction test = FunctionParser.parseInfix("\\tan(x)");
		assertEquals(1, test.evaluate(Map.of("x",Math.PI/4)), 0.0000001);
	}

	@Test void multiplyWorks() {
		GeneralFunction test = FunctionParser.parseInfix("(1/(x+1))*(\\cos x)*(x^2-1)");
		assertEquals(-1, test.evaluate(Map.of("x",0.0)));
	}

	@Test void addWorks() {
		GeneralFunction test = FunctionParser.parseInfix("(1/(x+1))+(\\sec(x))+(x^3-1)");
		assertEquals(1, test.evaluate(Map.of("x",0.0)));
	}

	@Test void noSpaces() {
		GeneralFunction test = FunctionParser.parseInfix("1+x*-3");
		assertEquals(-5, test.evaluate(Map.of("x",2.0)));
	}

	@Test void basicPolynomial() {
		GeneralFunction test = FunctionParser.parseInfix("x^2+5*x+4");
//		System.out.println(test);
		assertEquals(28, test.evaluate(Map.of("x",3.0)));
	}

	@Test void mediumPolynomial() {
		GeneralFunction test = FunctionParser.parseInfix("x^4+5*x^2+4");
//		System.out.println(test);
		assertEquals(130, test.evaluate(Map.of("x",3.0)));
	}

	@Test void orderOfOperations() {
		GeneralFunction test = FunctionParser.parseInfix("10*4-2*(4^2/4)/2/0.5+9");
//		System.out.println(test);
		assertEquals(41, test.evaluate(Map.of("x",1.0)));
	}

	@Test void multiplyingByAdjacency() {
		GeneralFunction test = FunctionParser.parseInfix("3x+5x^2");
		assertEquals(26, test.evaluate(Map.of("x",2.0)));
	}

	@Test void multiplyingByAdjacencyAndParentheses() {
		GeneralFunction test = FunctionParser.parseInfix("3(x+5x^2)");
		assertEquals(66, test.evaluate(Map.of("x",2.0)));
	}

	@Test void multiplyingAdjacentVariables() {
		GeneralFunction test = FunctionParser.parseInfix("xy");
		assertEquals(12, test.evaluate(Map.of("x",3.0, "y", 4.0)));
	}

	@Test void multiplyingAdjacentVariablesAndPowers() {
		GeneralFunction test = FunctionParser.parseInfix("x^2y^-1");
		assertEquals(8, test.evaluate(Map.of("x",4.0, "y", 2.0)));
	}

	@Test void multiplyingByAdjacencyAndParenthesesBackwards() {
		GeneralFunction test = FunctionParser.parseInfix("(x+5x^2)3");
		assertEquals(66, test.evaluate(Map.of("x",2.0)));
	}

	@Test void multiplyingByAdjacencyFunctions() {
		GeneralFunction test = FunctionParser.parseInfix("(5+x^2)(\\cos(x))");
		assertEquals(5, test.evaluate(Map.of("x",0.0)));
	}

	@Test void multiplyingByAdjacencyThreeTerms0() {
		GeneralFunction test = FunctionParser.parseInfix("(1/4)(2/3)\\sin(x)");
		assertEquals(0, test.evaluate(Map.of("x",0.0)));
	}

	@Test void multiplyingByAdjacencyThreeTerms1() {
		GeneralFunction test = FunctionParser.parseInfix("\\sin(\\pi/2)2(x)");
		assertEquals(2, test.evaluate(Map.of("x",1.0)), 0.01);
	}


	@Test void multiplyingAdjacencyLogb_2() {
		GeneralFunction test = FunctionParser.parseInfix("\\logb_{2}(x)");
		assertEquals(2, test.evaluate(Map.of("x",4.0)));
	}

	@Test void multiplyingByAdjacencyLogb_33() {
		GeneralFunction test = FunctionParser.parseInfix("\\logb_{33}(x)");
		assertEquals(2, test.evaluate(Map.of("x",1089.0)));
	}

	@Test void multiplyingAdjacencyLogb_y() {
		GeneralFunction test = FunctionParser.parseInfix("-\\logb_{y}(x)");
		assertEquals(-2, test.evaluate(Map.of("x",4.0, "y", 2.0)));
	}

	@Test void multiplyVariablesNoSpace() {
		GeneralFunction test = FunctionParser.parseInfix("2xy+3x");
		assertEquals(4, test.evaluate(Map.of("x",4.0, "y", -1.0)));
	}

	@Test void basicMap() {
		GeneralFunction test = FunctionParser.parseInfix("x");
		assertEquals(2, test.evaluate(Map.of("x", 2.0)));
	}

	@Test void overfullMap() {
		GeneralFunction test = FunctionParser.parseInfix("x");
		assertEquals(2, test.evaluate(Map.of("x", 2.0, "n", 1.7, "q", Double.MIN_VALUE)));
	}

	@Test void multivariableMap() {
		GeneralFunction test = FunctionParser.parseInfix("x+2y");
		assertEquals(5, test.evaluate(Map.of("x", 2.4, "y", 1.3)));
	}

	@Test void emptyMap() {
		GeneralFunction test = FunctionParser.parseInfix("\\ln(e)");
		assertEquals(1, test.evaluate(Map.of()));
	}

	@Test void nullMap() {
		GeneralFunction test = FunctionParser.parseInfix("\\ln(e)");
		assertEquals(1, test.evaluate(null));
	}

	@Test void usingEDoubles() {
		GeneralFunction test = FunctionParser.parseInfix("16E-6*1E6");
		assertEquals(16, test.evaluate(null));
	}

	@Test void negativeEscape() {
		GeneralFunction test = FunctionParser.parseInfix("-\\sin(\\pi/2)");
		assertEquals(-1, test.evaluate(null), .01);
	}

	@Test void frac() {
		GeneralFunction test = FunctionParser.parseInfix("-\\frac{3}{-2}");
		assertEquals(1.5, test.evaluate(null), .01);
	}

	@Test void exp() {
		GeneralFunction test = FunctionParser.parseInfix("\\exp(\\ln(x))");
		assertEquals(4, test.evaluate(Map.of("x", 4.0)), .01);
	}

	@Test void gcd() {
		GeneralFunction test = FunctionParser.parseSimplified("\\gcd(2, -4)");
		assertEquals(2, test.evaluate(Map.of()));
	}

	@Test void lcm() {
		GeneralFunction test = FunctionParser.parseSimplified("\\lcm(2,-4)");
		assertEquals(-4, test.evaluate(Map.of()));
	}

	@Test void mod() {
		GeneralFunction test = FunctionParser.parseSimplified("-4%3");
		assertEquals(-1, test.evaluate(Map.of()));
	}

	@Test void floor() {
		GeneralFunction test = FunctionParser.parseSimplified("floor(2.5)");
		assertEquals(2, test.evaluate(Map.of()));
	}

	@Test void ceil() {
		GeneralFunction test = FunctionParser.parseSimplified("ceil(2.5)");
		assertEquals(3, test.evaluate(Map.of()));
	}

	@Test void integerQuotient() {
		GeneralFunction test = FunctionParser.parseSimplified("4 // 3");
		assertEquals(1, test.evaluate(Map.of()));
	}

	@Test void integerQuotientNoSpaces() {
		GeneralFunction test = FunctionParser.parseSimplified("4//3");
		assertEquals(1, test.evaluate(Map.of()));
	}

	@Test void abs() {
		GeneralFunction test = FunctionParser.parseSimplified("|x|");
		assertEquals(4, test.evaluate(Map.of("x", -4.0)));
	}

	@Test void dirac() {
		GeneralFunction test = FunctionParser.parseSimplified("\\dirac(x)");
		assertEquals(0, test.evaluate(Map.of("x", -4.0)));
	}

	@Test void round() {
		GeneralFunction test = FunctionParser.parseSimplified("\\round(x)");
		assertEquals(2, test.evaluate(Map.of("x", 1.5)));
	}

	@Test void sign() {
		GeneralFunction test = FunctionParser.parseSimplified("\\sign(x)");
		assertEquals(-1, test.evaluate(Map.of("x", -4.0)));
	}


	@Test void arctrigParsing() {
		GeneralFunction test = FunctionParser.parseSimplified("\\arcsin(x)");
		assertEquals(new Asin(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccos(x)");
		assertEquals(new Acos(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arctan(x)");
		assertEquals(new Atan(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccsc(x)");
		assertEquals(new Acsc(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arcsec(x)");
		assertEquals(new Asec(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccot(x)");
		assertEquals(new Acot(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arcsinh(x)");
		assertEquals(new Asinh(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccosh(x)");
		assertEquals(new Acosh(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arctanh(x)");
		assertEquals(new Atanh(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccsch(x)");
		assertEquals(new Acsch(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arcsech(x)");
		assertEquals(new Asech(DefaultFunctions.X), test);
		test = FunctionParser.parseSimplified("\\arccoth(x)");
		assertEquals(new Acoth(DefaultFunctions.X), test);
	}

	@Test void arctrigEval() {
		GeneralFunction test = FunctionParser.parseSimplified("\\arcsin(0.7)");
		assertEquals(0.775397496611, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccos(0.7)");
		assertEquals(0.795398830184, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arctan(11.5)");
		assertEquals( 1.48405798812, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccsc(11.5)");
		assertEquals(0.0870664823474, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsec(11.5)");
		assertEquals(1.4837294445, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccot(-11.5)");
		assertEquals(3.05485421491, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsinh(-11.5)");
		assertEquals( -3.13737923732, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccosh(7)");
		assertEquals(2.63391579385, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arctanh(.6)");
		assertEquals(0.69314, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccsch(-.56)");
		assertEquals( -1.34348187431, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsech(.4)");
		assertEquals(1.56679923697, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccoth(1.5)");
		assertEquals(0.804718956217, test.evaluate(null), 0.001);
	}

	@Test void arcOfNormal() {
		GeneralFunction test = FunctionParser.parseSimplified("\\arcsin(sin(43))");
		assertEquals(-0.982297150257, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccos(cos(43))");
		assertEquals(0.9822971, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arctan(tan(11.5))");
		assertEquals(-1.06637061, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccsc(csc(11.5))");
		assertEquals(-1.06637, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsec(sec(11.5))");
		assertEquals(1.06637061, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccot(cot(-11.5))");
		assertEquals( 1.06637061, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsinh(sinh(-11.5))");
		assertEquals( -11.5, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccosh(cosh(7))");
		assertEquals(7, test.evaluate(null),  0.001);
		test = FunctionParser.parseSimplified("\\arctan(tan(.6))");
		assertEquals(.6, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccsch(csch(-.56))");
		assertEquals(-.56, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arcsech(sech(.4))");
		assertEquals(.4, test.evaluate(null), 0.001);
		test = FunctionParser.parseSimplified("\\arccoth(coth(1.5))");
		assertEquals(1.5, test.evaluate(null), 0.001);
	}

}
