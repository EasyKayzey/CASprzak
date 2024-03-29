import core.functions.GeneralFunction;
import core.functions.commutative.Product;
import core.functions.unitary.transforms.Differential;
import core.functions.unitary.transforms.Integral;
import org.junit.jupiter.api.Test;
import core.tools.defaults.DefaultFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static parsing.FunctionParser.parseInfix;
import static parsing.FunctionParser.parseSimplified;

public class DifferentialTest {
	@Test
	void basicDerivative1() {
		GeneralFunction test = parseSimplified("d/dx x");
		assertEquals(1, test.evaluate());
	}

	@Test
	void basicDerivative2() {
		GeneralFunction test = parseSimplified("d/dx{x}");
		assertEquals(1, test.evaluate());
	}

	@Test
	void basicDerivative3() {
		GeneralFunction test = parseSimplified("d/dx(x)");
		assertEquals(1, test.evaluate());
	}

	@Test
	void integral1() {
		GeneralFunction test = parseInfix("\\int[2x]\\dx");
		assertEquals(parseInfix("x^2"), test.simplify());
	}

	@Test
	void integralNullException() {
		Integral test = new Integral(parseSimplified("x"));
		assertThrows(Exception.class, test::execute);
		test.simplify();
	}

	@Test
	void integralDifferentialInside() {
		Integral test = new Integral(parseInfix("x\\dx"));
		assertEquals(parseInfix("x^2/2"), test.simplify());
	}

	@Test
	void differentialProductIntegral() {
		GeneralFunction test = new Product(new Integral(DefaultFunctions.X), new Differential("x"));
		assertEquals(parseInfix("x^2/2"), test.simplify());
	}

	@Test
	void differentialProductIntegral2() {
		GeneralFunction test = new Product(DefaultFunctions.TWO, new Integral(DefaultFunctions.X), new Differential("x"));
		assertEquals(parseInfix("x^2"), test.simplify());
	}

	@Test
	void differentialProductIntegralNested1() {
		GeneralFunction test = parseInfix("\\int(\\int(2x\\dx))\\dy");
		assertEquals(parseInfix("yx^2"), test.simplify());
	}

	@Test
	void integralThatBreaks() {
		GeneralFunction test = parseInfix("\\int(x^2\\dy)");
		assertEquals(parseInfix("y*x^2"), test.simplify());
	}

	@Test
	void integralThatBreaksManualCreation() {
		GeneralFunction test = new Integral(parseInfix("x^2"), "y");
		assertEquals(parseInfix("y*x^2"), test.simplify());
	}

	@Test
	void integral2() {
		GeneralFunction test = parseInfix("\\int[e^\\beta]\\d\\beta");
		assertEquals(parseInfix("e^\\beta"), test.simplify());
	}

	@Test
	void integral3() {
		GeneralFunction test = parseInfix("\\int[e^ϕ]\\dϕ");
		assertEquals(parseInfix("e^ϕ"), test.simplify());
	}
}
