import functions.Function;
import functions.commutative.Sum;
import functions.special.Constant;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;
import functions.unitary.transforms.Integral;
import tools.integration.IntegralTools;

import static org.junit.jupiter.api.Assertions.*;


public class IntegralTest {

    @Test
    void splitAdds() {
        Integral test1 = new Integral(Parser.parse("x+e^x"), 'x');
        Function test2 = new Sum(Parser.parse("0.5*x^2"), Parser.parse("e^x"));
        assertEquals(test1.execute() , test2);
    }

    @Test
    void unwrapsExponents() {
        Integral test1 = new Integral(Parser.parse("(x^2+2)^2"), 'x');
        Function test2 = new Sum(Parser.parse("(x^5)/5"), Parser.parse("4/3*x^3"), Parser.parse("4x"));
        assertEquals(test2, test1.execute());
    }

    @Test
    void stripConstantsSimple() {
        Function test1 = Parser.parse("3sin(x)");
        Pair<Function, Function> test2 = IntegralTools.stripConstants(test1, 'x');
        assertEquals(new Constant(3), test2.first);
        assertEquals(Parser.parse("sin(x)"), test2.second);
    }

    @Test
    void simpleExponentIntegral() {
        Integral test1 = new Integral(Parser.parse("e^x"), 'x');
        Function test2 = Parser.parse("e^x");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExponentIntegralWithConstant() {
        Integral test1 = new Integral(Parser.parse("2e^x"), 'x');
        Function test2 = Parser.parse("2e^x");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExponentIntegralWithLinerTermInExponent() {
        Integral test1 = new Integral(Parser.parse("2e^(2x-3)"), 'x');
        Function test2 = Parser.parse("e^(2x-3)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simplePowers() {
        Integral test1 = new Integral(Parser.parse("x^2"), 'x');
        Function test2 = Parser.parse("1/3 * x^3");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleTrig() {
        Integral test1 = new Integral(Parser.parse("cos(x)"), 'x');
        Function test2 = Parser.parse("sin(x)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleTrigWithConstants() {
        Integral test1 = new Integral(Parser.parse("4*cos(2x+3)"), 'x');
        Function test2 = Parser.parse("2*sin(2x+3)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleReciprocal() {
        Integral test1 = new Integral(Parser.parse("e^x/(1+e^x)"), 'x');
        Function test2 = Parser.parse("ln(1+e^x)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleSquareRoot() {
        Integral test1 = new Integral(Parser.parse("x*sqrt(1+x^2)"), 'x');
        Function test2 = Parser.parse("1/3 * (1+x^2)^(3/2)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExpUSub() {
        Integral test1 = new Integral(Parser.parse("x*e^(x^2)"), 'x');
        Function test2 = Parser.parse("1/2*e^(x^2)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExpUSubThatNoWork() {
        Integral test1 = new Integral(Parser.parse("2x*sin(x)*e^(x^2)"), 'x');
        assertThrows(IntegrationFailedException.class, test1::execute);
    }

    @Test
    void simpleOPIsOne() {
        Function test1 = new Integral(Parser.parse("cos(x)*sin(x)"), 'x').execute();
        Function test2 = Parser.parse("1/2*(sin(x))^2");
        Function test3 = Parser.parse("-1/2*(cos(x))^2");
        assertTrue(test1.equals(test2) || test1.equals(test3));
    }

    @Test
    void complexUSub() {
        Integral test1 = new Integral(Parser.parse("(cos(e^x))^2*sin(e^x)*e^x"), 'x');
        Function test2 = Parser.parse("-1/3*(cos(e^x))^3");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasic() {
        Integral test1 = new Integral(Parser.parse("x^y"), 'x');
        Function test2 = Parser.parse("1/(y+1)*x^(y+1)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasicWithNoX() {
        Integral test1 = new Integral(Parser.parse("y*z^y*ln(y)"), 'x');
        Function test2 = Parser.parse("y*z^y*ln(y)*x");
        assertEquals(test2.simplify(), test1.execute().simplify());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY1() {
        Integral test1 = new Integral(Parser.parse("e^(3x+y)"), 'x');
        Function test2 = Parser.parse("1/3*e^(3x+y)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY2() {
        Integral test1 = new Integral(Parser.parse("e^(3xy+y)"), 'x');
        Function test2 = Parser.parse("1/(3y)*e^(3xy+y)");
        assertEquals(test2, test1.execute());
    }
}
