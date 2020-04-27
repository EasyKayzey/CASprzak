import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.PolynomialTools;

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

}
