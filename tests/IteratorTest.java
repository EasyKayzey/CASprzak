import functions.Function;
import functions.binary.Pow;
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
}
