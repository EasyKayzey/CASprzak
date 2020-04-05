import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x','y');

	@Test void fxReturnsX() {
		Function test = parserX.parse("x");
		double b = test.evaluate(2);
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() {
		Function test = parserX.parse("x ^ x + 2 + 5 * x");
		double b = test.evaluate(3);
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() {
        Function test = parserXY.parse("x + y + y");
		double b = test.evaluate(3, 4);
		assertEquals(11, b);
	}

	@Test void logbWorks() {
		Function test = parserX.parse("logb 3 x");
		double b = test.evaluate(81);
		assertEquals(4, b);
	}

	@Test void trigWorks() {
		Function test = parserX.parse("( sin x ) ^ 2 + ( cos x ) ^ 2");
		double b = test.evaluate(81);
		assertEquals(1, b);
	}

	@Test void eToTheXWorks() {
		Function test = parserX.parse("e ^ x");
		double b = test.evaluate(0);
		assertEquals(1, b);
	}

	@Test void lnWorks() {
		Function test = parserX.parse("ln e");
		double b = test.evaluate(0);
		assertEquals(1, b);
	}

	@Test void tanWorks() {
		Function test = parserX.parse("tan x");
		double b = test.evaluate(Math.PI/4);
		assertEquals(1, b);
	}

	@Test void multiplyWorks() {
		Function test = parserX.parse("( 1 / ( x + 1 ) ) * ( cos x ) * ( x ^ 2 - 1 )");
		double b = test.evaluate(0);
		assertEquals(-1, b);
	}

	@Test void addWorks() {
		Function test = parserX.parse("( 1 / ( x + 1 ) ) + ( sec x ) + ( x ^ 3 - 1 )");
		double b = test.evaluate(0);
		assertEquals(1, b);
	}



}
