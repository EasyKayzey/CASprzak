import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ComboTest {
	@Test
	void basicFactorial1() {
		Function test = Parser.parse("x!+2");
		assertEquals(8, test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void parenFactorial1() {
		Function test = Parser.parse("(x)!+2");
		assertEquals(8, test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void basicFactorial2() {
		Function test = Parser.parse("3!+2");
		assertEquals(8, test.evaluate(Map.of('x', 3.5)));
	}

	@Test
	void parenFactorial2() {
		Function test = Parser.parse("(2*x-54)!+2");
		assertEquals(26, test.evaluate(Map.of('x', 29.0)));
	}

	@Test
	void absFactorial1() {
		Function test = Parser.parse("(|x|)!"); // Might want to make this test use precedence when prefix precedence is added
		assertEquals(6, test.evaluate(Map.of('x', -3.0)));
	}

	@Test
	void functionFactorial1() {
		Function test = Parser.parse("(ln(e^3))!"); // Might want to make this test use precedence when prefix precedence is added
		assertEquals(6, test.evaluate(Map.of('x', -3.0)));
	}
}
