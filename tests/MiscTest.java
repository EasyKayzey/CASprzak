import org.junit.jupiter.api.Test;
import tools.MiscTools;
import tools.ParsingTools;
import tools.helperclasses.AbstractMutablePair;
import tools.helperclasses.AbstractPair;
import tools.helperclasses.MutablePair;
import tools.helperclasses.Pair;

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
}
