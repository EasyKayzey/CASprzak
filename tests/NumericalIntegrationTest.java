import show.ezkz.casprzak.core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.core.tools.singlevariable.NumericalIntegration;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumericalIntegrationTest {

    @Test
    void simpleTrigIntegral() {
        GeneralFunction function = FunctionParser.parseInfix("\\sin(x)");
        double test = NumericalIntegration.simpsonsRule(function, 0, Math.PI);
        assertEquals(2, test, 0.0001);
    }

    @Test
    void simplePolynomialIntegral() {
        GeneralFunction function = FunctionParser.parseInfix("x^2");
        double test = NumericalIntegration.simpsonsRule(function, 0, 1);
        assertEquals(0.33333333333333333333333333333333333333, test, 0.0001);
    }

    @Test
    void simpleTrigIntegralWithError() {
        GeneralFunction function = FunctionParser.parseInfix("\\sin(x)");
        double[] test = NumericalIntegration.simpsonsRuleWithError(function, 0, Math.PI);
        assertArrayEquals(new double[]{2, 1e-15}, test, 0.0001);
    }

}
