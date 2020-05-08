import config.Settings;
import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.MiscTools;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LatexTest {

	@Test
	void adjMultiply() {
		GeneralFunction test = Parser.parse("\\pi\\pi");
		assertEquals(10, test.evaluate(null), .3);
	}

	@Test
	void testVar() {
		GeneralFunction test = Parser.parse("\\pi\\Gamma");
		assertEquals(10, test.evaluate(Map.of(MiscTools.getCharacter("\\Gamma"), 3.2)), .3);
	}

	@Test
	void latexNot() {
		GeneralFunction test = Parser.parse("\\pi x");
		assertEquals(10, test.evaluate(Map.of('x', 3.2)), .3);
	}

	@Test
	void rightNumber() {
		GeneralFunction test = Parser.parse("\\pi 2");
		assertEquals(6.3, test.evaluate(Map.of('x', 3.2)), .3);
	}

	@Test
	void leftNumber() {
		GeneralFunction test = Parser.parse("2\\pi ");
		assertEquals(6.3, test.evaluate(Map.of('x', 3.2)), .3);
	}

	@Test
	void leftParen() {
		GeneralFunction test = Parser.parse("(1+1)\\pi ");
		assertEquals(6.3, test.evaluate(Map.of('x', 3.2)), .3);
	}

	@Test
	void rightParen() {
		GeneralFunction test = Parser.parse("\\pi (1+1)");
		assertEquals(6.3, test.evaluate(Map.of('x', 3.2)), .3);
	}

	@Test
	void log() {
		GeneralFunction test = Parser.parse("\\log(x)+\\log(1)");
		assertEquals(1, test.evaluate(Map.of('x', 10.0)), .3);
	}

	@Test
	void noEscape() {
		boolean temp = Settings.enforceEscapes;
		Settings.enforceEscapes = false;
		GeneralFunction test = Parser.parse("pi (1+1)+sin(pi/2)");
		assertEquals(7.3, test.evaluate(Map.of('x', 3.2)), .3);
		Settings.enforceEscapes = temp;
	}

}
