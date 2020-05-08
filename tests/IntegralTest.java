import functions.GeneralFunction;
import functions.commutative.Sum;
import functions.special.Constant;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.exceptions.IntegrationFailedException;
import tools.helperclasses.Pair;
import functions.unitary.transforms.Integral;
import tools.integration.IntegralTools;

import static org.junit.jupiter.api.Assertions.*;


public class IntegralTest {

    @Test
    void splitAdds() {
        Integral test1 = new Integral(FunctionParser.parseInfix("x+e^x"), 'x');
        GeneralFunction test2 = new Sum(FunctionParser.parseInfix("0.5*x^2"), FunctionParser.parseInfix("e^x"));
        assertEquals(test1.execute() , test2);
    }

    @Test
    void unwrapsExponents() {
        Integral test1 = new Integral(FunctionParser.parseInfix("(x^2+2)^2"), 'x');
        GeneralFunction test2 = new Sum(FunctionParser.parseInfix("(x^5)/5"), FunctionParser.parseInfix("4/3*x^3"), FunctionParser.parseInfix("4x"));
        assertEquals(test2, test1.execute());
    }

    @Test
    void stripConstantsSimple() {
        GeneralFunction test1 = FunctionParser.parseInfix("3\\sin(x)");
        Pair<GeneralFunction, GeneralFunction> test2 = IntegralTools.stripConstants(test1, 'x');
        assertEquals(new Constant(3), test2.first);
        assertEquals(FunctionParser.parseInfix("\\sin(x)"), test2.second);
    }

    @Test
    void simpleExponentIntegral() {
        Integral test1 = new Integral(FunctionParser.parseInfix("e^x"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("e^x");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExponentIntegralWithConstant() {
        Integral test1 = new Integral(FunctionParser.parseInfix("2e^x"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("2e^x");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExponentIntegralWithLinerTermInExponent() {
        Integral test1 = new Integral(FunctionParser.parseInfix("2e^(2x-3)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("e^(2x-3)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simplePowers() {
        Integral test1 = new Integral(FunctionParser.parseInfix("x^2"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/3 * x^3");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleTrig() {
        Integral test1 = new Integral(FunctionParser.parseInfix("\\cos(x)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(x)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleTrigWithConstants() {
        Integral test1 = new Integral(FunctionParser.parseInfix("4*\\cos(2x+3)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("2*\\sin(2x+3)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleReciprocal() {
        Integral test1 = new Integral(FunctionParser.parseInfix("e^x/(1+e^x)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("\\ln(1+e^x)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleSquareRoot() {
        Integral test1 = new Integral(FunctionParser.parseInfix("x*\\sqrt(1+x^2)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/3 * (1+x^2)^(3/2)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExpUSub() {
        Integral test1 = new Integral(FunctionParser.parseInfix("x*e^(x^2)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/2*e^(x^2)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void simpleExpUSubThatNoWork() {
        Integral test1 = new Integral(FunctionParser.parseInfix("2x*\\sin(x)*e^(x^2)"), 'x');
        assertThrows(IntegrationFailedException.class, test1::execute);
    }

    @Test
    void simpleOPIsOne() {
        GeneralFunction test1 = new Integral(FunctionParser.parseInfix("\\cos(x)*\\sin(x)"), 'x').execute();
        GeneralFunction test2 = FunctionParser.parseInfix("1/2*(\\sin(x))^2");
        GeneralFunction test3 = FunctionParser.parseInfix("-1/2*(\\cos(x))^2");
        assertTrue(test1.equals(test2) || test1.equals(test3));
    }

    @Test
    void complexUSub() {
        Integral test1 = new Integral(FunctionParser.parseInfix("(\\cos(e^x))^2*\\sin(e^x)*e^x"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("-1/3*(\\cos(e^x))^3");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasic() {
        Integral test1 = new Integral(FunctionParser.parseInfix("x^y"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/(y+1)*x^(y+1)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasicWithNoX() {
        Integral test1 = new Integral(FunctionParser.parseInfix("y*z^y*\\ln(y)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("y*z^y*\\ln(y)*x");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY1() {
        Integral test1 = new Integral(FunctionParser.parseInfix("e^(3x+y)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/3*e^(3x+y)");
        assertEquals(test2, test1.execute());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY2() {
        Integral test1 = new Integral(FunctionParser.parseInfix("e^(3xy+y)"), 'x');
        GeneralFunction test2 = FunctionParser.parseInfix("1/(3y)*e^(3xy+y)");
        assertEquals(test2, test1.execute());
    }
}
