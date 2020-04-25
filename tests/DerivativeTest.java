import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivativeTest {

    @Test
    void constantsGive0() {
        Function test = Parser.parse("2");
        assertEquals(0, test.getSimplifiedDerivative(0).evaluate(3467));
    }

    @Test
    void variablesGive1() {
        Function test = Parser.parse("x");
        assertEquals(1, test.getSimplifiedDerivative(0).evaluate(3467));
    }

    @Test
    void simpleSumAndProductDerivatives() {
        Function test;
        test = Parser.parse("x+3");
        assertEquals(1, test.getSimplifiedDerivative(0).evaluate(9));
        test = Parser.parse("x*7");
        assertEquals(7, test.getSimplifiedDerivative(0).evaluate(9));
        test = Parser.parse("2*x+7");
        assertEquals(2, test.getSimplifiedDerivative(0).evaluate(9));
        test = Parser.parse("2*(x+7)");
        assertEquals(2, test.getSimplifiedDerivative(0).evaluate(9));
    }

    @Test
    void longDerivative() {
        Function test = Parser.parse("(sin(x^0.5+1) * e^(x^0.5)) * x^-0.5").getSimplifiedDerivative(0).getSimplifiedDerivative(0);
        assertEquals(-0.13874, test.evaluate(4), 0.0001);
//        System.out.println("Second derivative simplified once:");
//        System.out.println(test);
//        System.out.println("Second derivative simplified twice:");
//        System.out.println(test.simplify());
    }

    @Test
    void arcTrigTests() {
        Function test;
        test = Parser.parse("4acos(x)-10atan(x)");
        assertEquals(-12.773, test.getSimplifiedDerivative(0).evaluate(0.456), 0.01);
        test = Parser.parse("asin(x)+x");
        assertEquals(2.638, test.getSimplifiedDerivative(0).evaluate(0.792), 0.01);
    }

    @Test
    void getNthDerivative() {
        Function test1;
        test1 = Parser.parse("x^2");
        assertEquals(test1.getNthDerivative(0, 0), test1);
    }

    @Test
    void logbTests() {
        Function test;
        test = Parser.parse("logb_{10}(x)");
        assertEquals(0.659, test.getSimplifiedDerivative(0).evaluate(0.659), 0.01);
    }

}
