import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonCommutativeTest {

	@Test
	void basicSubtract() {
		GeneralFunction test = Parser.parse("1-x+4-3+x+4-1-x");
		assertEquals(2, test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void subtractWithMultiply() {
		GeneralFunction test = Parser.parse("x-2*x+1");
		assertEquals(-2, test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void basicNCPolynomial() {
		GeneralFunction test = Parser.parse("x^2-5*x+4");
//		System.out.println(test);
		assertEquals(-2,test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void mediumNCPolynomial() {
		GeneralFunction test = Parser.parse("x^4-5*x^2+4");
//		System.out.println(test);
		assertEquals(40,test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void negativeConstant() {
		GeneralFunction test = Parser.parse("-3");
		assertEquals(-3, test.evaluate(Map.of('x', 17.0)));
	}

	@Test
	void leadingNegative() {
		GeneralFunction test = Parser.parse("-x");
		assertEquals(-3, test.evaluate(Map.of('x', 3.0)));
	}

	@Test
	void multiVariableDivisionWithNegatives() {
		GeneralFunction test = Parser.parse("-x/-y+-y/3");
		assertEquals(-1.0/3,test.evaluate(Map.of('x', 2.0, 'y', 3.0)), .001);
	}

	@Test
	void dividingNegatives() {
		GeneralFunction test = Parser.parse("1/-x");
		assertEquals(-1, test.evaluate(Map.of('x', 1.0)));
	}

	@Test
	void dividingByOneTerm() {
		GeneralFunction test = Parser.parse("1/x^2+x");
		assertEquals(2, test.evaluate(Map.of('x', 1.0)));
	}

	@Test
	void dividingByPolynomial() {
		GeneralFunction test = Parser.parse("1/(x^2+x-1)");
		assertEquals(1, test.evaluate(Map.of('x', 1.0)));
	}

	@Test
	void dividingAndMultiplying1() {
		GeneralFunction test = Parser.parse("1/x*x");
		assertEquals(1, test.evaluate(Map.of('x', 1.786)));
	}

	@Test
	void dividingAndMultiplying2() {
		GeneralFunction test = Parser.parse("1/x * x");
		assertEquals(1, test.evaluate(Map.of('x', 1.786)));
	}
}
