import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DerivativeTest {
    @Test void constantsGive0() {
        Function test = Function.makeFunction("2");
        assertEquals(0, test.derivative("x").evaluate(new String[] {"x"}, new double[]{3467}));
    }

    @Test void variablesGive1() {
        Function test = Function.makeFunction("x");
        assertEquals(1, test.derivative("x").evaluate(new String[] {"x"}, new double[]{3467}));
    }



}
