import config.Settings;
import functions.GeneralFunction;
import functions.commutative.Product;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.DefaultFunctions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoEscapeTest {

	@Test
	void sinPi() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test = FunctionParser.parseInfix("pi (1+1)+sin(pi/2)");
		assertEquals(7.3, test.evaluate(Map.of("x", 3.2)), .3);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void log() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test = FunctionParser.parseInfix("log(x)+log(1)");
		assertEquals(1, test.evaluate(Map.of("x", 10.0)), .3);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void distributeTerms() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseInfix("sin(x)*(1+5x)");
		GeneralFunction test2 = FunctionParser.parseInfix("sin(x)+5*x*sin(x)");
		assertEquals(((Product)test1).distributeAll(), test2);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void exp() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test = FunctionParser.parseInfix("exp(ln(x))");
		assertEquals(4, test.evaluate(Map.of("x", 4.0)), .01);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void simpleInverseLnAndExp() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseInfix("exp(ln(x))");
		GeneralFunction test2 = FunctionParser.parseInfix("ln(exp(x))");
		assertEquals(test1.simplify(), test2.simplify());
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void simpleInverseTrig() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseInfix("asin(sin(x))");
		GeneralFunction test2 = FunctionParser.parseInfix("cos(acos(x))");
		assertEquals(test1.simplify(), test2.simplify());
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void inverseChain() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseInfix("asin(acos(exp(ln(sec(asec(cos(sin(x))))))))");
		assertEquals(DefaultFunctions.X, test1.simplify()); // this isn"t actually correct based on ranges, so if you add that this will break
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void expInverses() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseSimplified("ln(e^logb_e(exp(x)))");
		assertEquals(DefaultFunctions.X, test1);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void logFOC() {
		boolean temp = Settings.enforceEscapedFunctions;
		Settings.enforceEscapedFunctions = false;
		GeneralFunction test1 = FunctionParser.parseSimplified("log(100)");
		assertEquals(DefaultFunctions.TWO, test1);
		Settings.enforceEscapedFunctions = temp;
	}

	@Test
	void spaceLatex() {
		GeneralFunction test = FunctionParser.parseInfix("1 + sin(x)");
		assertEquals(1, test.evaluate(Map.of("x", 3.0)), .3);
	}
}
