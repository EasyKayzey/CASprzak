import show.ezkz.casprzak.core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.core.tools.PolynomialTools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
