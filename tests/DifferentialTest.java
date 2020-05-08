import functions.GeneralFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
}
