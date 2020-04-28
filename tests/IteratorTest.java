import functions.Function;
import functions.binary.Pow;
import functions.special.Variable;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorTest {
	@Test
	void testCommutative1() {
		Function test = Parser.parseSimplified("x^2+x^3+x^4");
		int i = 0;
		boolean io = true;
		for (Function f : test) {
			i++;
			io = io && f instanceof Pow;
		}
		assertTrue(io);
		assertEquals(i, 3);
	}

	@Test
	void testBinary1() {
		Function test = Parser.parseSimplified("x^2");
		int i = 0;
		for (Function f : test) {
			i++;
		}
		assertEquals(i, 2);
	}

	@Test
	void testUnitary1() {
		Function test = Parser.parseSimplified("sin x");
		int i = 0;
		for (Function f : test) {
			i++;
			assertTrue(f instanceof Variable);
		}
		assertEquals(i, 1);
	}

	@Test
	void testSpecial1() {
		Function test = Parser.parseSimplified("x");
		int i = 0;
		for (Function f : test) {
			i++;
		}
		assertEquals(i, 0);
	}
}
