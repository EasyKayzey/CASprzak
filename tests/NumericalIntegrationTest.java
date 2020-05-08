import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.singlevariable.NumericalIntegration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumericalIntegrationTest {

    @Test
    void simpleTrigIntegral() {
        GeneralFunction function = Parser.parse("\\sin(x)");
        double test = NumericalIntegration.simpsonsRule(function, 0, Math.PI);
        assertEquals(2, test, 0.0001);
    }

    @Test
    void simplePolynomialIntegral() {
        GeneralFunction function = Parser.parse("x^2");
        double test = NumericalIntegration.simpsonsRule(function, 0, 1);
        assertEquals(0.33333333333333333333333333333333333333, test, 0.0001);
    }

    @Test
    void simpleTrigIntegralWithError() {
        GeneralFunction function = Parser.parse("\\sin(x)");
        double[] test = NumericalIntegration.simpsonsRuleWithError(function, 0, Math.PI);
        assertArrayEquals(new double[]{2, 1e-15}, test, 0.0001);
    }

}
