import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.core.tools.exceptions.IntegrationFailedException;
import show.ezkz.casprzak.core.tools.helperclasses.Pair;
import show.ezkz.casprzak.core.functions.unitary.transforms.Integral;
import show.ezkz.casprzak.core.tools.IntegralTools;

import static org.junit.jupiter.api.Assertions.*;


public class IntegralTest {

    @Test
    void splitAdds() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("x+e^x"), "x");
        GeneralFunction test2 = new Sum(FunctionParser.parseSimplified("0.5*x^2"), FunctionParser.parseSimplified("e^x"));
        assertEquals(test1.execute().simplify(), test2);
    }

    @Test
    void unwrapsExponents() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("(x^2+2)^2"), "x");
        GeneralFunction test2 = new Sum(FunctionParser.parseSimplified("(x^5)/5"), FunctionParser.parseSimplified("4/3*x^3"), FunctionParser.parseSimplified("4x"));
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void stripConstantsSimple() {
        GeneralFunction test1 = FunctionParser.parseSimplified("3\\sin(x)");
        Pair<GeneralFunction, GeneralFunction> test2 = IntegralTools.stripConstantsRespectTo(test1, "x");
        assertEquals(new Constant(3), test2.getFirst());
        assertEquals(FunctionParser.parseSimplified("\\sin(x)"), test2.getSecond());
    }

    @Test
    void simpleExponentIntegral() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("e^x"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("e^x");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleExponentIntegralWithConstant() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("2e^x"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("2e^x");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleExponentIntegralWithLinerTermInExponent() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("2e^(2x-3)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("e^(2x-3)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simplePowers() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("x^2"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/3 * x^3");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleTrig() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("\\cos(x)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("\\sin(x)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleTrigWithConstants() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("4*\\cos(2x+3)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("2*\\sin(2x+3)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleReciprocal() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("e^x/(1+e^x)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("\\ln(1+e^x)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleSquareRoot() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("x*\\sqrt(1+x^2)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/3 * (1+x^2)^(3/2)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleExpUSub() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("x*e^(x^2)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/2*e^(x^2)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void simpleExpUSubThatNoWork() {
        Integral test1 = new Integral(FunctionParser.parseSimplified("2x*\\sin(x)*e^(x^2)"), "x");
        assertThrows(IntegrationFailedException.class, test1::execute);
    }

    @Test
    void simpleOPIsOne() throws Exception {
        GeneralFunction test1 = new Integral(FunctionParser.parseSimplified("\\cos(x)*\\sin(x)"), "x").execute().simplify();
        GeneralFunction test2 = FunctionParser.parseSimplified("1/2*(\\sin(x))^2");
        GeneralFunction test3 = FunctionParser.parseSimplified("-1/2*(\\cos(x))^2");
        assertTrue(test1.equals(test2) || test1.equals(test3));
    }

    @Test
    void complexUSub() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseInfix("(\\cos(e^x))^2*\\sin(e^x)*e^x"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("-1/3*(\\cos(e^x))^3");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasic() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("x^y"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/(y+1)*x^(y+1)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasicWithoutX() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("y*z^y*\\ln(y)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("y*z^y*\\ln(y)*x");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasicWithX() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("y*z^y*\\ln(y)x^3"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("y*z^y*\\ln(y)*x^4/4");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasicWithXUnsimplified() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("y*z^y*\\ln(y)x^3"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("y*z^y*\\ln(y)*x^4/4");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY1() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("e^(3x+y)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/3*e^(3x+y)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void severalVariableBasicFunctionOfXAndY2() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("e^(3xy+y)"), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("1/(3y)*e^(3xy+y)");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void noIntegrand() throws Exception {
        Integral test1 = new Integral(new Product(), "x");
        GeneralFunction test2 = FunctionParser.parseSimplified("x");
        assertEquals(test2, test1.execute().simplify());
    }

    @Test
    void integralNonAsciiCharacter1() throws Exception {
        Integral test1 = new Integral(FunctionParser.parseSimplified("e^ϕ"), "ϕ");
        GeneralFunction test2 = FunctionParser.parseSimplified("e^ϕ");
        assertEquals(test2, test1.execute().simplify());
    }

}
