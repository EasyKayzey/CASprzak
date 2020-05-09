import functions.GeneralFunction;
import functions.unitary.transforms.Integral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static parsing.FunctionParser.*;

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

}
