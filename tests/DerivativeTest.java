
import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivativeTest {
    @Test void constantsGive0() {
        Parser parser = new Parser('x');
        Function test = parser.parse("2");
        assertEquals(0, test.getDerivative(0).evaluate(new double[]{3467}));
    }

    @Test void variablesGive1() {
        Parser parser = new Parser('x');
        Function test = parser.parse("x");
        assertEquals(1, test.getDerivative(0).evaluate(new double[]{3467}));
    }



}
