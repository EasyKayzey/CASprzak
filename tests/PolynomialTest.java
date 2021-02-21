import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.functiongenerators.HermitePolynomial;
import show.ezkz.casprzak.core.tools.functiongenerators.LegrendePolynomial;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.core.tools.PolynomialTools;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		assertEquals(new Product(DefaultFunctions.TWO, test1), HermitePolynomial.hermitePolynomial(1));
		test1 = new Variable("j");
		assertEquals(new Product(DefaultFunctions.TWO, test1), HermitePolynomial.hermitePolynomial(1, "j"));
		assertEquals(new Pow(new Constant(-.25), DefaultFunctions.PI), HermitePolynomial.normalHermitePolynomial(0));
		assertEquals(new Product(new Constant(Math.sqrt(2)), new Pow(new Constant(-.25), DefaultFunctions.PI), new Variable("t")), HermitePolynomial.normalHermitePolynomial(1, "t"));
		assertEquals(new Pow(new Constant(.25), DefaultFunctions.PI), HermitePolynomial.normalizingConstant(0));

	}

}
