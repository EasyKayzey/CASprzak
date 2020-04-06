import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonCommutativeTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x','y');

	@Test
	void basicSubtract() {
		Function test = parserX.parse("1-x+4-3+x+4-1-x");
		assertEquals(2, test.evaluate(3));
	}

	@Test
	void subtractWithMultiply() {
		Function test = parserX.parse("x-2*x+1");
		assertEquals(-2, test.evaluate(3));
	}

}
