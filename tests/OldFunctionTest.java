import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class OldFunctionTest {
	@Test void fxReturnsX() {
		OldFunction test = OldFunction.makeFunction("x");
		double b = test.evaluate(new String[]{"x"}, new double[]{2});
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() {
		OldFunction test = OldFunction.makeFunction("x ^ x + 2 + 5 * x");
		double b = test.evaluate(new String[]{"x"}, new double[]{3});
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() {
		OldFunction test = OldFunction.makeFunction("x + y + y");
		double b = test.evaluate(new String[]{"x", "y"}, new double[]{3, 4});
		assertEquals(11, b);
	}

	@Test void logbWorks() {
		OldFunction test = OldFunction.makeFunction("logb 3 x");
		double b = test.evaluate(new String[]{"x"}, new double[]{81});
		assertEquals(4, b);
	}

	@Test void trigWorks() {
		OldFunction test = OldFunction.makeFunction("( sin x ) ^ 2 + ( cos x ) ^ 2");
		double b = test.evaluate(new String[]{"x"}, new double[]{81});
		assertEquals(1, b);
	}


}
