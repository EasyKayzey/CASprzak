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
}
