import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FunctionTest {
	Parser parserX = new Parser('x');
	Parser parserXY = new Parser('x','y');

	@Test void fxReturnsX() throws Exception {
		Function test = parserX.parse("x");
		double b = test.evaluate(new double[]{2});
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() throws Exception {
		Function test = parserX.parse("x ^ x + 2 + 5 * x");
		double b = test.evaluate(new double[]{3});
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() throws Exception {
        Function test = parserXY.parse("x + y + y");
		double b = test.evaluate(new double[]{3, 4});
		assertEquals(11, b);
	}

	@Test void logbWorks() throws Exception {
		Function test = parserX.parse("logb 3 x");
		double b = test.evaluate(new double[]{81});
		assertEquals(4, b);
	}

	@Test void trigWorks() throws Exception {
		Function test = parserX.parse("( sin x ) ^ 2 + ( cos x ) ^ 2");
		double b = test.evaluate(new double[]{81});
		assertEquals(1, b);
	}


}
