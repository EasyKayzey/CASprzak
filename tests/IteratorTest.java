import functions.GeneralFunction;
import functions.binary.Pow;
import functions.endpoint.Constant;
import functions.endpoint.Variable;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ChainOfInstanceofChecks")
public class IteratorTest {
	@Test
	void testCommutative1() {
		GeneralFunction test = FunctionParser.parseSimplified("x^2+x^3+x^4");
		int i = 0;
		boolean io = true;
		for (GeneralFunction f : test) {
			i++;
			io = io && f instanceof Pow;
		}
		assertTrue(io);
		assertEquals(i, 3);
	}

	@Test
	void testBinary1() {
		GeneralFunction test = FunctionParser.parseSimplified("x^2");
		int i = 0;
		for (GeneralFunction f : test) {
			i++;
		}
		assertEquals(i, 2);
	}

	@Test
	void testUnitary1() {
		GeneralFunction test = FunctionParser.parseSimplified("\\sin x");
		int i = 0;
		for (GeneralFunction f : test) {
			i++;
			assertTrue(f instanceof Variable);
		}
		assertEquals(i, 1);
	}

	@Test
	void testSpecial1() {
		GeneralFunction test = FunctionParser.parseSimplified("x");
		int i = 0;
		for (GeneralFunction f : test) {
			i++;
		}
		assertEquals(i, 0);
	}

	@Test
	void testRecursive1() {
		GeneralFunction test = FunctionParser.parseSimplified("1+2x+x^2+x^3+x^4");
		int i = 0;
		int j = 0;
		for (GeneralFunction f : test) {
			for (GeneralFunction g : f) {
				if (g instanceof Variable) {
					i++;
				} else if (g instanceof Constant) {
					j++;
				}
			}
			if (f instanceof Variable) {
				i++;
			} else if (f instanceof Constant) {
				j++;
			}
		}
		assertEquals(i, 4);
		assertEquals(j, 5);
	}
}
