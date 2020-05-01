import functions.Function;
import functions.commutative.Sum;
import functions.unitary.Ln;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;
import tools.integral.IntegralsTools;

import static org.junit.jupiter.api.Assertions.*;


public class IntegralTest {

    @Test
    void splitAdds() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("x+e^x"), 'x');
        Function test2 = new Sum(new Ln.Integral(Parser.parse("x"), 'x'), new Ln.Integral(Parser.parse("e^x"), 'x'));
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
        Ln.Integral test1 = new Ln.Integral(Parser.parse("e^x"), 'x');
        Function test2 = Parser.parse("e^x");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExponentIntegralWithConstant() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("2e^x"), 'x');
        Function test2 = Parser.parse("2e^x");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExponentIntegralWithLinerTermInExponent() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("2e^(2x-3)"), 'x');
        Function test2 = Parser.parse("e^(2x-3)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simplePowers() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("x^2"), 'x');
        Function test2 = Parser.parse("1/3 * x^3");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleTrig() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("cos(x)"), 'x');
        Function test2 = Parser.parse("sin(x)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleTrigWithConstants() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("4*cos(2x+3)"), 'x');
        Function test2 = Parser.parse("2*sin(2x+3)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleReciprocal() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("e^x/(1+e^x)"), 'x');
        Function test2 = Parser.parse("ln(1+e^x)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleSquareRoot() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("x*sqrt(1+x^2)"), 'x');
        Function test2 = Parser.parse("1/3 * (1+x^2)^(3/2)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExpUSub() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("x*e^(x^2)"), 'x');
        Function test2 = Parser.parse("1/2*e^(x^2)");
        assertEquals(test2, test1.integrate());
    }

    @Test
    void simpleExpUSubThatNoWork() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("2x*sin(x)*e^(x^2)"), 'x');
        assertThrows(IntegrationFailedException.class, test1::integrate);
    }

    @Test
    void simpleOPIsOne() {
        Function test1 = new Ln.Integral(Parser.parse("cos(x)*sin(x)"), 'x').integrate();
        Function test2 = Parser.parse("1/2*(sin(x))^2");
        Function test3 = Parser.parse("-1/2*(cos(x))^2");
        assertTrue(test1.equals(test2) || test1.equals(test3));
    }

    @Test
    void complexUSub() {
        Ln.Integral test1 = new Ln.Integral(Parser.parse("(cos(e^x))^2*sin(e^x)*e^x"), 'x');
        Function test2 = Parser.parse("-1/3*(cos(e^x))^3");
        assertEquals(test2, test1.integrate());
    }
}
