import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplitTest {
	final Parser parserX = new Parser('x');
	final Parser parserXY = new Parser('x', 'y');

	@Test void noSpaces() {
		Function test = parserX.parse("1+x*-3");
		assertEquals(-5, test.evaluate(2));
	}
}