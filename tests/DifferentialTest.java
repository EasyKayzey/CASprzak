import functions.GeneralFunction;
import functions.unitary.transforms.Integral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static parsing.FunctionParser.parseInfix;
import static parsing.FunctionParser.parseSimplified;

public class DifferentialTest {
	@Test
	void basicDerivative1() {
		GeneralFunction test = parseSimplified("d/dx x");
		assertEquals(1, test.evaluate(null));
	}

	@Test
	void basicDerivative2() {
		GeneralFunction test = parseSimplified("d/dx{x}");
		assertEquals(1, test.evaluate(null));
	}

	@Test
	void basicDerivative3() {
		GeneralFunction test = parseSimplified("d/dx(x)");
		assertEquals(1, test.evaluate(null));
	}

	@Test
	void integral1() {
		GeneralFunction test = parseInfix("\\int[2x]\\dx");
		assertTrue(test instanceof Integral);
	}

	@Test
	void integralNullException() {
		Integral test = new Integral(parseSimplified("x"));
		assertThrows(Exception.class, test::execute);
		assertThrows(Exception.class, () -> test.substituteVariable('x', parseSimplified("y")));
		test.simplify();
	}

	@Test
	void integralDifferentialInside() {
		Integral test = new Integral(parseInfix("x\\dx"));
		assertEquals(parseInfix("x^2/2"), test.simplify());
	}

}
