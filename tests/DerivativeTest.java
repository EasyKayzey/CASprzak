
import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivativeTest {
    final Parser parser = new Parser('x');

    @Test
    void constantsGive0() {
        Function test = parser.parse("2");
        assertEquals(0, test.getSimplifiedDerivative(0).evaluate(3467));
    }

    @Test
    void variablesGive1() {
        Function test = parser.parse("x");
        assertEquals(1, test.getSimplifiedDerivative(0).evaluate(3467));
    }

    @Test
    void simpleSumAndProductDerivatives() {
        Function test;
//        test = parser.parse("x+3");
//        assertEquals(1, test.getSimplifiedDerivative(0).evaluate(9));
//        test = parser.parse("x*7");
//        assertEquals(7, test.getSimplifiedDerivative(0).evaluate(9));
//        test = parser.parse("2*x+7");
//        assertEquals(2, test.getSimplifiedDerivative(0).evaluate(9));
//        test = parser.parse("2*(x+7)");
//        assertEquals(2, test.getSimplifiedDerivative(0).evaluate(9));
    }

    @Test
    void longDerivative() {
        Function test = parser.parse("(sin(x^0.5+1) * e^(x^0.5)) * x^-0.5").getSimplifiedDerivative(0).getSimplifiedDerivative(0);
        assertEquals(-0.13874, test.evaluate(4), 0.0001);
        System.out.println("Second derivative simplified once:");
        System.out.println(test);
        System.out.println("Second derivative simplified twice:");
        System.out.println(test.simplify());
    }

}
