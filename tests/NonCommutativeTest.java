import parsing.Parser;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonCommutativeTest {

	@Test
	void basicSubtract() {
		Function test = Parser.parse("1-x+4-3+x+4-1-x");
		assertEquals(2, test.evaluate(3));
	}

	@Test
	void subtractWithMultiply() {
		Function test = Parser.parse("x-2*x+1");
		assertEquals(-2, test.evaluate(3));
	}

	@Test
	void basicNCPolynomial() {
		Function test = Parser.parse("x^2-5*x+4");
//		System.out.println(test);
		assertEquals(-2,test.evaluate(3));
	}

	@Test
	void mediumNCPolynomial() {
		Function test = Parser.parse("x^4-5*x^2+4");
//		System.out.println(test);
		assertEquals(40,test.evaluate(3));
	}

	@Test
	void negativeConstant() {
		Function test = Parser.parse("-3");
		assertEquals(-3, test.evaluate(17));
	}

	@Test
	void leadingNegative() {
		Function test = Parser.parse("-x");
		assertEquals(-3, test.evaluate(3));
	}

	@Test
	void multiVariableDivisionWithNegatives() {
		Function test = Parser.parse("-x/-y+-y/3");
		assertEquals(-1.0/3,test.evaluate(2,3), .001);
	}

	@Test
	void dividingNegatives() {
		Function test = Parser.parse("1/-x");
		assertEquals(-1, test.evaluate(1));
	}

	@Test
	void dividingByOneTerm() {
		Function test = Parser.parse("1/x^2+x");
		assertEquals(2, test.evaluate(1));
	}

	@Test
	void dividingByPolynomial() {
		Function test = Parser.parse("1/(x^2+x-1)");
		assertEquals(1, test.evaluate(1));
	}

	@Test
	void dividingAndMultiplying1() {
		Function test = Parser.parse("1/x*x");
		assertEquals(1, test.evaluate(1.786));
	}

	@Test
	void dividingAndMultiplying2() {
		Function test = Parser.parse("1/x * x");
		assertEquals(1, test.evaluate(1.786));
	}
}
