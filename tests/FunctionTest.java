import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x','y');

	@Test void fxReturnsX() {
		Function test = parserX.parse("x");
		assertEquals(2, test.evaluate(2));
	}

	@Test void basicFunctionsWithX() {
		Function test = parserX.parse("x^x+2+5*x");
		assertEquals(44, test.evaluate(3));
	}

	@Test void multipleVariablesWorks() {
        Function test = parserXY.parse("x+y+y");
		assertEquals(11, test.evaluate(3, 4));
	}

	@Test void logbWorks() {
		Function test = parserX.parse("logb_3(x)");
		assertEquals(4, test.evaluate(81));
	}

	@Test void trigWorks() {
		Function test = parserX.parse("(sin(x))^2+(cos x)^2");
		assertEquals(1, test.evaluate(81));
	}

	@Test void eToTheXWorks() {
		Function test = parserX.parse("e^x");
		assertEquals(1, test.evaluate(0));
	}

	@Test void lnWorks() {
		Function test = parserX.parse("ln(e)");
		assertEquals(1, test.evaluate(0));
	}

	@Test void tanWorks() {
		Function test = parserX.parse("tan(x)");
		assertEquals(1, test.evaluate(Math.PI/4), 0.0000001);
	}

	@Test void multiplyWorks() {
		Function test = parserX.parse("(1/(x+1))*(cos x)*(x^2-1)");
		assertEquals(-1, test.evaluate(0));
	}

	@Test void addWorks() {
		Function test = parserX.parse("(1/(x+1))+(sec(x))+(x^3-1)");
		assertEquals(1, test.evaluate(0));
	}

	@Test void noSpaces() {
		Function test = parserX.parse("1+x*-3");
		assertEquals(-5, test.evaluate(2));
	}

	@Test void basicPolynomial() {
		Function test = parserX.parse("x^2+5*x+4");
//		System.out.println(test);
		assertEquals(28, test.evaluate(3));
	}

	@Test void mediumPolynomial() {
		Function test = parserX.parse("x^4+5*x^2+4");
//		System.out.println(test);
		assertEquals(130, test.evaluate(3));
	}

	@Test void orderOfOperations() {
		Function test = parserX.parse("10*4-2*(4^2/4)/2/0.5+9");
//		System.out.println(test);
		assertEquals(41, test.evaluate(1));
	}

	@Test void multiplyingByAdjacency() {
		Function test = parserX.parse("3x+5x^2");
		assertEquals(26, test.evaluate(2));
	}

	@Test void multiplyingByAdjacencyAndParentheses() {
		Function test = parserX.parse("3(x+5x^2)");
		assertEquals(66, test.evaluate(2));
	}

	@Test void multiplyingAdjacentVariables() {
		Function test = parserXY.parse("xy");
		assertEquals(12, test.evaluate(3, 4));
	}

	@Test void multiplyingAdjacentVariablesAndPowers() {
		Function test = parserXY.parse("x^2y^-1");
		assertEquals(8, test.evaluate(4, 2));
	}

	@Test void multiplyingByAdjacencyAndParenthesesBackwards() {
		Function test = parserX.parse("(x+5x^2)3");
		assertEquals(66, test.evaluate(2));
	}

	@Test void multiplyingByAdjacencyFunctions() {
		Function test = parserX.parse("(5+x^2)(cos(x))");
		assertEquals(5, test.evaluate(0));
	}








}
