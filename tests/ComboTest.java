import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ComboTest {
	@Test
	void basicFactorial1() {
		GeneralFunction test = FunctionParser.parseInfix("x!+2");
		assertEquals(8, test.evaluate(Map.of("x", 3.0)));
	}

	@Test
	void parenFactorial1() {
		GeneralFunction test = FunctionParser.parseInfix("(x)!+2");
		assertEquals(8, test.evaluate(Map.of("x", 3.0)));
	}

	@Test
	void basicFactorial2() {
		GeneralFunction test = FunctionParser.parseInfix("3!+2");
		assertEquals(8, test.evaluate(Map.of("x", 3.5)));
	}

	@Test
	void parenFactorial2() {
		GeneralFunction test = FunctionParser.parseInfix("(2*x-54)!+2");
		assertEquals(26, test.evaluate(Map.of("x", 29.0)));
	}

	@Test
	void absFactorial1() {
		GeneralFunction test = FunctionParser.parseInfix("(|x|)!"); // Might want to make this test use precedence when prefix precedence is added
		assertEquals(6, test.evaluate(Map.of("x", -3.0)));
	}

	@Test
	void functionFactorial1() {
		GeneralFunction test = FunctionParser.parseInfix("(\\ln(e^3))!"); // Might want to make this test use precedence when prefix precedence is added
		assertEquals(6, test.evaluate(Map.of("x", -3.0)));
	}

	@Test
	void test6C4() {
		GeneralFunction test = FunctionParser.parseSimplified("6C4");
		assertEquals(15, test.evaluate(null));
	}

	@Test
	void test6P4() {
		GeneralFunction test = FunctionParser.parseSimplified("6P4");
		assertEquals(360, test.evaluate(null));
	}

	@Test
	void test6Cx() {
		GeneralFunction test = FunctionParser.parseSimplified("6Cx");
		assertEquals(20, test.evaluate(Map.of("x", 3.0)));
	}
}
