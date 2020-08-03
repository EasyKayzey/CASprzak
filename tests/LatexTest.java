import functions.GeneralFunction;
import functions.endpoint.Variable;
import functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LatexTest {

	@Test
	void adjMultiply() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi\\pi");
		assertEquals(10, test.evaluate(), .3);
	}

	@Test
	void testVar() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi \\Gamma");
		assertEquals(10, test.evaluate(Map.of("Γ", 3.2)), .3);
	}

	@Test
	void latexNot() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi x");
		assertEquals(10, test.evaluate(Map.of("x", 3.2)), .3);
	}

	@Test
	void rightNumber() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi 2");
		assertEquals(6.3, test.evaluate(Map.of("x", 3.2)), .3);
	}

	@Test
	void leftNumber() {
		GeneralFunction test = FunctionParser.parseInfix("2\\pi ");
		assertEquals(6.3, test.evaluate(Map.of("x", 3.2)), .3);
	}

	@Test
	void leftParen() {
		GeneralFunction test = FunctionParser.parseInfix("(1+1)\\pi ");
		assertEquals(6.3, test.evaluate(Map.of("x", 3.2)), .3);
	}

	@Test
	void rightParen() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi (1+1)");
		assertEquals(6.3, test.evaluate(Map.of("x", 3.2)), .3);
	}

	@Test
	void log() {
		GeneralFunction test = FunctionParser.parseInfix("\\log(x)+\\log(1)");
		assertEquals(1, test.evaluate(Map.of("x", 10.0)), .3);
	}

	@Test
	void spaceEscapedLatex() {
		GeneralFunction test = FunctionParser.parseInfix("1 + \\sin(x)");
		assertEquals(1, test.evaluate(Map.of("x", 3.0)), .3);
	}

	@Test
	void adjacentWords1() {
		GeneralFunction test = FunctionParser.parseInfix("\\pi\\Gamma");
		assertEquals(6.3, test.evaluate(Map.of("Γ", 2.)), .3);
	}

	@Test
	void adjacentWords2() {
		GeneralFunction test = FunctionParser.parseInfix("\\epsilon\\Gamma\\epsilon\\pi");
		assertEquals(6.3, test.evaluate(Map.of("ϵ", 1., "Γ", 2.)), .3);
	}

	@Test
	void sinx() {
		GeneralFunction test = FunctionParser.parseInfix("sinx");
		assertEquals(new Sin(new Variable("x")), test);
	}
}
