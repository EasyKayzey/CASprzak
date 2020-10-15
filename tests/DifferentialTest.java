import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.unitary.transforms.Differential;
import show.ezkz.casprzak.core.functions.unitary.transforms.Integral;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static show.ezkz.casprzak.parsing.FunctionParser.parseInfix;
import static show.ezkz.casprzak.parsing.FunctionParser.parseSimplified;

public class DifferentialTest {

	private static final SimplificationSettings settings = DefaultSimplificationSettings.aggressive;

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
		assertEquals(parseInfix("x^2"), test.simplify(settings));
	}

	@Test
	void integralNullException() {
		Integral test = new Integral(parseSimplified("x"));
		assertThrows(Exception.class, test::execute);
		test.simplify(settings);
	}

	@Test
	void integralDifferentialInside() {
		Integral test = new Integral(parseInfix("x\\dx"));
		assertEquals(parseInfix("x^2/2"), test.simplify(settings));
	}

	@Test
	void differentialProductIntegral() {
		GeneralFunction test = new Product(new Integral(DefaultFunctions.X), new Differential("x"));
		assertEquals(parseInfix("x^2/2"), test.simplify(settings));
	}

	@Test
	void differentialProductIntegral2() {
		GeneralFunction test = new Product(DefaultFunctions.TWO, new Integral(DefaultFunctions.X), new Differential("x"));
		assertEquals(parseInfix("x^2"), test.simplify(settings));
	}

	@Test
	void differentialProductIntegralNested1() {
		GeneralFunction test = parseInfix("\\int(\\int(2x\\dx))\\dy");
		assertEquals(parseInfix("yx^2"), test.simplify(settings));
	}

	@Test
	void integralThatBreaks() {
		GeneralFunction test = parseInfix("\\int(x^2\\dy)");
		assertEquals(parseInfix("y*x^2"), test.simplify(settings));
	}

	@Test
	void integralThatBreaksManualCreation() {
		GeneralFunction test = new Integral(parseInfix("x^2"), "y");
		assertEquals(parseInfix("y*x^2"), test.simplify(settings));
	}

	@Test
	void integral2() {
		GeneralFunction test = parseInfix("\\int[e^\\beta]\\d\\beta");
		assertEquals(parseInfix("e^\\beta"), test.simplify(settings));
	}

	@Test
	void integral3() {
		GeneralFunction test = parseInfix("\\int[e^ϕ]\\dϕ");
		assertEquals(parseInfix("e^ϕ"), test.simplify(settings));
	}
}
