import core.config.Settings;
import core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.functions.endpoint.Variable;
import core.functions.unitary.specialcases.Exp;
import core.tools.defaults.DefaultFunctions;
import core.tools.functiongenerators.HermitePolynomial;
import core.tools.functiongenerators.LaguerrePolynomial;
import core.tools.functiongenerators.LegrendePolynomial;
import core.tools.functiongenerators.TaylorSeries;
import parsing.FunctionParser;
import core.tools.PolynomialTools;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static core.tools.defaults.DefaultFunctions.*;

public class PolynomialTest {
    @Test
    void isSimpleMonomial() {
        GeneralFunction test = FunctionParser.parseInfix("x");
        assertTrue(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithAConstant() {
        GeneralFunction test = FunctionParser.parseInfix("3x");
        assertTrue(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithAPower() {
        GeneralFunction test = FunctionParser.parseInfix("x^2");
        assertTrue(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithPowerAndConstant() {
        GeneralFunction test = FunctionParser.parseInfix("3x^2");
        assertTrue(PolynomialTools.isMonomial(test));
    }

    @Test
    void isGeneralMonomial() {
        GeneralFunction test = FunctionParser.parseInfix("3.32145*x^(-4.4352)");
        assertTrue(PolynomialTools.isGeneralMonomial(test));
    }

    @Test
    void isSimplePolynomialOfAMonomial() {
        GeneralFunction test = FunctionParser.parseInfix("3x^2");
        assertTrue(PolynomialTools.isPolynomial(test));
    }

    @Test
    void isNotSimplePolynomial() {
        GeneralFunction test = FunctionParser.parseInfix("5x^-1");
        assertFalse(PolynomialTools.isPolynomial(test));
    }

    @Test
    void isSimplePolynomial() {
        GeneralFunction test = FunctionParser.parseSimplified("5x^3+x^2+5x+3269485");
        assertTrue(PolynomialTools.isPolynomial(test));
    }

    @Test
	void legendrePolynomial() {
    	Variable test1 = new Variable(Settings.singleVariableDefault);
    	assertEquals(test1, LegrendePolynomial.legrendePolynomial(1));
		test1 = new Variable("j");
		assertEquals(test1, LegrendePolynomial.legrendePolynomial(1, "j"));
		assertEquals(new Constant(1/Math.sqrt(2)), LegrendePolynomial.normalLegrendePolynomial(0));
		assertEquals(new Product(new Constant(Math.sqrt(3/2.)), new Variable("t")), LegrendePolynomial.normalLegrendePolynomial(1, "t"));
		assertEquals(new Constant(Math.sqrt(2/5.)), LegrendePolynomial.normalizingConstant(2));

	}

	@Test
	void hermitePolynomial() {
		Variable test1 = new Variable(Settings.singleVariableDefault);
		assertEquals(new Product(TWO, test1), HermitePolynomial.hermitePolynomial(1));
		test1 = new Variable("j");
		assertEquals(new Product(TWO, test1), HermitePolynomial.hermitePolynomial(1, "j"));
		assertEquals(new Pow(new Constant(-.25), PI), HermitePolynomial.normalHermitePolynomial(0));
		assertEquals(new Product(new Constant(Math.sqrt(2)), new Pow(new Constant(-.25), PI), new Variable("t")), HermitePolynomial.normalHermitePolynomial(1, "t"));
		assertEquals(new Pow(new Constant(.25), PI), HermitePolynomial.normalizingConstant(0));

	}

	@Test
	void laguerrePolynomial() {
		Variable test1 = new Variable(Settings.singleVariableDefault);
		assertEquals(new Sum(ONE, negative(test1)), LaguerrePolynomial.laguerrePolynomial(1));
		test1 = new Variable("j");
		assertEquals(new Sum(ONE, negative(test1)), LaguerrePolynomial.laguerrePolynomial(1, "j"));
		assertEquals(ONE, LaguerrePolynomial.laguerrePolynomial(0));
		assertEquals(FunctionParser.parseSimplified("(1/2 x^2-2x+1)"), LaguerrePolynomial.laguerrePolynomial(2));
		assertEquals(FunctionParser.parseSimplified("(-1/6 x^3+ 3/2 x^2-3x+1)"), LaguerrePolynomial.laguerrePolynomial(3));

	}

	@Test
	void taylorSeriesVariable1() {
		Variable test1 = new Variable(Settings.singleVariableDefault);
		assertEquals(new Sum(ONE, test1), TaylorSeries.makeTaylorSeries(new Exp(test1), 1));
		test1 = new Variable("j");
		assertEquals(new Sum(ONE, test1), TaylorSeries.makeTaylorSeries(new Exp(test1), 1));
	}

	@Test
	void taylorSeriesVariable2() {
		Variable x = new Variable(Settings.singleVariableDefault);
		Variable y = new Variable("y");
		assertThrows(UnsupportedOperationException.class, () -> TaylorSeries.makeTaylorSeries(new Exp(new Sum(x, y)), 1));
	}

}
