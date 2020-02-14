import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class OldFunctionTest {
	@Test void fxReturnsX() throws Exception {
	    Parser parser = new Parser(new char[]{'x'});
		Function test = parser.parse("x");
		double b = test.evaluate(new double[]{2});
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() throws Exception {
        Parser parser = new Parser(new char[]{'x'});
        Function test = parser.parse("x ^ x + 2 + 5 * x");
		double b = test.evaluate(new double[]{3});
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() throws Exception {
        Parser parser = new Parser(new char[]{'x', 'y'});
        Function test = parser.parse("x + y + y");
		double b = test.evaluate(new double[]{3, 4});
		assertEquals(11, b);
	}

	@Test void logbWorks() throws Exception {
        Parser parser = new Parser(new char[]{'x'});
        Function test = parser.parse("logb 3 x");
		double b = test.evaluate(new double[]{81});
		assertEquals(4, b);
	}

	@Test void trigWorks() throws Exception {
        Parser parser = new Parser(new char[]{'x'});
        Function test = parser.parse("( sin x ) ^ 2 + ( cos x ) ^ 2");
		double b = test.evaluate(new double[]{81});
		assertEquals(1, b);
	}


}
