import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {

	@Test void fxReturnsX() {
		GeneralFunction test = Parser.parse("x");
		assertEquals(2, test.evaluate(Map.of('x',2.0)));
	}

	@Test void basicFunctionsWithX() {
		GeneralFunction test = Parser.parse("x^x+2+5*x");
		assertEquals(44, test.evaluate(Map.of('x',3.0)));
	}

	@Test void multipleVariablesWorks() {
        GeneralFunction test = Parser.parse("x+y+y");
		assertEquals(11, test.evaluate(Map.of('x',3.0, 'y', 4.0)));
	}

	@Test void logbWorks() {
		GeneralFunction test = Parser.parse("\\logb_3(x)");
		assertEquals(4, test.evaluate(Map.of('x',81.0)));
	}

	@Test void trigWorks() {
		GeneralFunction test = Parser.parse("(\\sin(x))^2+(\\cos x)^2");
		assertEquals(1, test.evaluate(Map.of('x',81.0)));
	}

	@Test void eToTheXWorks() {
		GeneralFunction test = Parser.parse("e^x");
		assertEquals(1, test.evaluate(Map.of('x',0.0)));
	}

	@Test void lnWorks() {
		GeneralFunction test = Parser.parse("\\ln(e)");
		assertEquals(1, test.evaluate(Map.of('x',1.5)));
	}

	@Test void tanWorks() {
		GeneralFunction test = Parser.parse("\\tan(x)");
		assertEquals(1, test.evaluate(Map.of('x',Math.PI/4)), 0.0000001);
	}

	@Test void multiplyWorks() {
		GeneralFunction test = Parser.parse("(1/(x+1))*(\\cos x)*(x^2-1)");
		assertEquals(-1, test.evaluate(Map.of('x',0.0)));
	}

	@Test void addWorks() {
		GeneralFunction test = Parser.parse("(1/(x+1))+(\\sec(x))+(x^3-1)");
		assertEquals(1, test.evaluate(Map.of('x',0.0)));
	}

	@Test void noSpaces() {
		GeneralFunction test = Parser.parse("1+x*-3");
		assertEquals(-5, test.evaluate(Map.of('x',2.0)));
	}

	@Test void basicPolynomial() {
		GeneralFunction test = Parser.parse("x^2+5*x+4");
//		System.out.println(test);
		assertEquals(28, test.evaluate(Map.of('x',3.0)));
	}

	@Test void mediumPolynomial() {
		GeneralFunction test = Parser.parse("x^4+5*x^2+4");
//		System.out.println(test);
		assertEquals(130, test.evaluate(Map.of('x',3.0)));
	}

	@Test void orderOfOperations() {
		GeneralFunction test = Parser.parse("10*4-2*(4^2/4)/2/0.5+9");
//		System.out.println(test);
		assertEquals(41, test.evaluate(Map.of('x',1.0)));
	}

	@Test void multiplyingByAdjacency() {
		GeneralFunction test = Parser.parse("3x+5x^2");
		assertEquals(26, test.evaluate(Map.of('x',2.0)));
	}

	@Test void multiplyingByAdjacencyAndParentheses() {
		GeneralFunction test = Parser.parse("3(x+5x^2)");
		assertEquals(66, test.evaluate(Map.of('x',2.0)));
	}

	@Test void multiplyingAdjacentVariables() {
		GeneralFunction test = Parser.parse("xy");
		assertEquals(12, test.evaluate(Map.of('x',3.0, 'y', 4.0)));
	}

	@Test void multiplyingAdjacentVariablesAndPowers() {
		GeneralFunction test = Parser.parse("x^2y^-1");
		assertEquals(8, test.evaluate(Map.of('x',4.0, 'y', 2.0)));
	}

	@Test void multiplyingByAdjacencyAndParenthesesBackwards() {
		GeneralFunction test = Parser.parse("(x+5x^2)3");
		assertEquals(66, test.evaluate(Map.of('x',2.0)));
	}

	@Test void multiplyingByAdjacencyFunctions() {
		GeneralFunction test = Parser.parse("(5+x^2)(\\cos(x))");
		assertEquals(5, test.evaluate(Map.of('x',0.0)));
	}

	@Test void multiplyingByAdjacencyThreeTerms0() {
		GeneralFunction test = Parser.parse("(1/4)(2/3)\\sin(x)");
		assertEquals(0, test.evaluate(Map.of('x',0.0)));
	}

	@Test void multiplyingByAdjacencyThreeTerms1() {
		GeneralFunction test = Parser.parse("\\sin(\\pi/2)2(x)");
		assertEquals(2, test.evaluate(Map.of('x',1.0)), 0.01);
	}


	@Test void multiplyingAdjacencyLogb_2() {
		GeneralFunction test = Parser.parse("\\logb_{2}(x)");
		assertEquals(2, test.evaluate(Map.of('x',4.0)));
	}

	@Test void multiplyingByAdjacencyLogb_33() {
		GeneralFunction test = Parser.parse("\\logb_{33}(x)");
		assertEquals(2, test.evaluate(Map.of('x',1089.0)));
	}

	@Test void multiplyingAdjacencyLogb_y() {
		GeneralFunction test = Parser.parse("-\\logb_{y}(x)");
		assertEquals(-2, test.evaluate(Map.of('x',4.0, 'y', 2.0)));
	}

	@Test void multiplyVariablesNoSpace() {
		GeneralFunction test = Parser.parse("2xy+3x");
		assertEquals(4, test.evaluate(Map.of('x',4.0, 'y', -1.0)));
	}

	@Test void basicMap() {
		GeneralFunction test = Parser.parse("x");
		assertEquals(2, test.evaluate(Map.of('x', 2.0)));
	}

	@Test void overfullMap() {
		GeneralFunction test = Parser.parse("x");
		assertEquals(2, test.evaluate(Map.of('x', 2.0, 'n', 1.7, 'q', Double.MIN_VALUE)));
	}

	@Test void multivariableMap() {
		GeneralFunction test = Parser.parse("x+2y");
		assertEquals(5, test.evaluate(Map.of('x', 2.4, 'y', 1.3)));
	}

	@Test void emptyMap() {
		GeneralFunction test = Parser.parse("\\ln(e)");
		assertEquals(1, test.evaluate(Map.of()));
	}

	@Test void nullMap() {
		GeneralFunction test = Parser.parse("\\ln(e)");
		assertEquals(1, test.evaluate(null));
	}

	@Test void usingEDoubles() {
		GeneralFunction test = Parser.parse("16E-6*1E6");
		assertEquals(16, test.evaluate(null));
	}

	@Test void negativeEscape() {
		GeneralFunction test = Parser.parse("-\\sin(\\pi/2)");
		assertEquals(-1, test.evaluate(null), .01);
	}

}
