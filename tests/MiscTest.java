import functions.special.Variable;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.MiscTools;
import tools.ParsingTools;
import tools.helperclasses.AbstractMutablePair;
import tools.helperclasses.AbstractPair;
import tools.helperclasses.MutablePair;
import tools.helperclasses.Pair;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MiscTest {
	@Test
	void immutablePair() {
		AbstractPair<Integer, Double> pair = new Pair<>(1, .5);
		assertEquals(1, pair.getFirst());
		assertEquals(.5, pair.getSecond());
	}

	@Test
	void mutablePair() {
		AbstractMutablePair<Integer, Double> pair = new MutablePair<>(1, .5);
		assertEquals(1, pair.getFirst());
		assertEquals(.5, pair.getSecond());
		pair.setFirst(4);
		pair.setSecond(5.0);
		assertEquals(4, pair.getFirst());
		assertEquals(5, pair.getSecond());
	}

	@Test
	void miscParsing() {
		assertTrue(ParsingTools.parseBoolean("truE"));
		assertFalse(ParsingTools.parseBoolean("falSE"));
		assertEquals(4, ParsingTools.toInteger(4.00000000002));
	}

	@Test
	void gcd() {
		assertEquals(4, MiscTools.gcd(8, 12));
		assertEquals(1, MiscTools.gcd(9, 7));
	}

	@Test
	void hashCode1() {
		assertEquals(FunctionParser.parseSimplified("x+y").hashCode(), FunctionParser.parseSimplified("y+x").hashCode());
		assertEquals(FunctionParser.parseSimplified("\\sec(x)*\\tan(x)*(5*(\\sec(x))^2+(\\tan(x))^2)").hashCode(), FunctionParser.parseSimplified("(5*(\\sec(x))^2+(\\tan(x))^2)*\\sec(x)*\\tan(x)").hashCode());
	}

	@Test
	void rounding() {
		assertTrue(ParsingTools.isAlmostInteger(-186.000000000000000001));
		assertEquals(-186, ParsingTools.toInteger(-186.00000000000000001));
	}

	@Test
	void multiCharVariable() {
		assertEquals(3, new Variable("abc").evaluate(Map.of("abc", 3.0)));
		assertEquals(3, FunctionParser.parseSimplified("\\el+1").evaluate(Map.of("\\el", 2.0)));
	}
}
