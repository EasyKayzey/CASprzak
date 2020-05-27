import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;

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
}
