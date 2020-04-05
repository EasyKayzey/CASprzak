import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x','y');

	@Test void fxReturnsX() {
		Function test = parserX.parse("x");
		double b = test.evaluate(new double[]{2});
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() {
		Function test = parserX.parse("x ^ x + 2 + 5 * x");
		double b = test.evaluate(new double[]{3});
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() {
        Function test = parserXY.parse("x + y + y");
		double b = test.evaluate(new double[]{3, 4});
		assertEquals(11, b);
	}

	@Test void logbWorks() {
		Function test = parserX.parse("logb 3 x");
		double b = test.evaluate(new double[]{81});
		assertEquals(4, b);
	}

	@Test void trigWorks() {
		Function test = parserX.parse("( sin x ) ^ 2 + ( cos x ) ^ 2");
		double b = test.evaluate(new double[]{81});
		assertEquals(1, b);
	}


}
