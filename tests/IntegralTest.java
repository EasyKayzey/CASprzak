import functions.Function;
import functions.commutative.Sum;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;
import tools.integral.Integral;
import tools.integral.IntegralsTools;

import static org.junit.jupiter.api.Assertions.*;


public class IntegralTest {

    @Test
    void splitAdds() {
        Integral test1 = new Integral(Parser.parse("x+e^x"), 'x');
        Function test2 = new Sum(new Integral(Parser.parse("x"), 'x'), new Integral(Parser.parse("e^x"), 'x'));
        assertEquals(test1.integrate() , test2);
    }

    @Test
    void stripConstantsSimple() {
        Function test1 = Parser.parse("3sin(x)");
        Pair<Double, Function> test2 = IntegralsTools.stripConstants(test1);
        assertEquals(3, test2.first);
        assertEquals(Parser.parse("sin(x)"), test2.second);
    }

    @Test
    void simpleExponentIntegral() {
        Integral test1 = new Integral(Parser.parse("e^x"), 'x');
        Function test2 = Parser.parse("e^x");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExponentIntegralWithConstant() {
        Integral test1 = new Integral(Parser.parse("2e^x"), 'x');
        Function test2 = Parser.parse("2e^x");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExponentIntegralWithLinerTermInExponent() {
        Integral test1 = new Integral(Parser.parse("2e^(2x-3)"), 'x');
        Function test2 = Parser.parse("e^(2x-3)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simplePowers() {
        Integral test1 = new Integral(Parser.parse("x^2"), 'x');
        Function test2 = Parser.parse("1/3 * x^3");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleTrig() {
        Integral test1 = new Integral(Parser.parse("cos(x)"), 'x');
        Function test2 = Parser.parse("sin(x)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleTrigWithConstants() {
        Integral test1 = new Integral(Parser.parse("4*cos(2x+3)"), 'x');
        Function test2 = Parser.parse("2*sin(2x+3)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleReciprocal() {
        Integral test1 = new Integral(Parser.parse("e^x/(1+e^x)"), 'x');
        Function test2 = Parser.parse("ln(1+e^x)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleSquareRoot() {
        Integral test1 = new Integral(Parser.parse("x*sqrt(1+x^2)"), 'x');
        Function test2 = Parser.parse("1/3 * (1+x^2)^(3/2)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExpUSub() {
        Integral test1 = new Integral(Parser.parse("x*e^(x^2)"), 'x');
        Function test2 = Parser.parse("1/2*e^(x^2)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExpUSubThatNoWork() {
        Integral test1 = new Integral(Parser.parse("2x*sin(x)*e^(x^2)"), 'x');
        assertThrows(IntegrationFailedException.class, test1::integrate);
    }

    @Test
    void simpleOPIsOne() {
        Function test1 = new Integral(Parser.parse("cos(x)*sin(x)"), 'x').integrate();
        Function test2 = Parser.parse("1/2*(sin(x))^2");
        Function test3 = Parser.parse("-1/2*(cos(x))^2");
        assertTrue(test1.equals((Object)test2) || test1.equals((Object)test3)); // TODO fix that this requires a cast (this is an equals problem)
    }

    @Test
    void complexUSub() {
        Integral test1 = new Integral(Parser.parse("(cos(e^x))^2*sin(e^x)*e^x"), 'x');
        Function test2 = Parser.parse("1/3*(cos(e^x))^3");
        assertEquals(test2, test1.integrate());
    }
}
