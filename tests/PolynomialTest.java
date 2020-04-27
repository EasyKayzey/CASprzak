import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.PolynomialTools;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PolynomialTest {
    @Test
    void isSimpleMonomial() {
        Function test = Parser.parse("x");
        assert(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithAConstant() {
        Function test = Parser.parse("3x");
        assert(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithAPower() {
        Function test = Parser.parse("x^2");
        assert(PolynomialTools.isMonomial(test));
    }

    @Test
    void isSimpleMonomialWithPowerAndConstant() {
        Function test = Parser.parse("3x^2");
        assert(PolynomialTools.isMonomial(test));
    }

    @Test
    void isGeneralMonomial() {
        Function test = Parser.parse("3.32145*x^(-4.4352)");
        assert(PolynomialTools.isGeneralMonomial(test));
    }

    @Test
    void isSimplePolynomialOfAMonomial() {
        Function test = Parser.parse("3x^2");
        assert(PolynomialTools.isPolynomial(test));
    }

    @Test
    void isNotSimplePolynomial() {
        Function test = Parser.parse("5x^-1");
        assertFalse(PolynomialTools.isPolynomial(test));
    }

    @Test
    void isSimplePolynomial() {
        Function test = Parser.parse("5x^3+x^2+5x+3269485");
        assert(PolynomialTools.isPolynomial(test));
    }
}
