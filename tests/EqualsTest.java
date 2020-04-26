import core.Settings;
import functions.Function;
import functions.commutative.Multiply;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EqualsTest {

    @Test
    void unitaryEqualsUnitary() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("sin(x)");
        assertEquals(test1, test2);
    }

    @Test
    void variablesAreDifferent() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("sin(y)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentUnitaryFunctions() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("cos(x)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentFunctionTypes() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void multiplyAndAdd() {
        Function test1 = Parser.parse("x*2");
        Function test2 = Parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableConstant() {
        Function test1 = Parser.parse("1+2x");
        Function test2 = Parser.parse("2x+1");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariables() {
        Function test1 = Parser.parse("x+y");
        Function test2 = Parser.parse("y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedVariablesWithPowers() {
        Function test1 = Parser.parse("x^2+x^3");
        Function test2 = Parser.parse("x^3+x^2");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableMultiply() {
        Function test1 = Parser.parse("x+2y");
        Function test2 = Parser.parse("2y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderMultiplies() {
        Function test1 = Parser.parse("3x+2y");
        Function test2 = Parser.parse("2y + 3x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariablesMul() {
        Function test1 = Parser.parse("x*y");
        Function test2 = Parser.parse("yx");
        assertEquals(test1, test2);
    }

    @Test
    void switchedVariablesWithPowersMul() {
        Function test1 = Parser.parse("x^2*x^3");
        Function test2 = Parser.parse("x^3*x^2");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableMultiplyMul() {
        Function test1 = Parser.parse("x*2*y");
        Function test2 = Parser.parse("2*y*x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderMultipliesMul() {
        Function test1 = Parser.parse("3x*2y");
        Function test2 = Parser.parse("2y*3x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderComplicated() {
        Function test1 = Parser.parse("x^2+1+y+3sin(x)+2x");
        Function test2 = Parser.parse("3(sin(x))+y+x^2+2x+1");
        assertEquals(test1, test2);
    }


    @Test
    void distributeTerms() {
        Function test1 = Parser.parse("sin(x)*(1+5x)");
        Function test2 = Parser.parse("sin(x)+5*x*sin(x)");
        assertEquals(((Multiply)test1).distributeAll(), test2);
    }

    @Test
    void distributeExponents() {
        Settings.distributeExponents = true;
        Function test1 = Parser.parse("(2xy)^2");
        Function test2 = Parser.parse("4x^2y^2");
        assertEquals(test1, test2);
    }

    @Test
    void addExponents() {
        Function test1 = Parser.parse("x^2*x^4");
        Function test2 = Parser.parse("x^6");
        assertEquals(test1, test2);
    }

    @Test
    void combineLikeTermsMultExample() {
        Function test1 = Parser.parse("(3+x)(2+x)(1+x)");
        Function test2 = Parser.parse("x^3+11x+6x^2+6");
        assertEquals(test1, test2);
    }

    @Test
    void simplifySimpleExponents() {
        Function test1 = Parser.parse("(x+1)^1");
        Function test2 = Parser.parse("x+1");
        assertEquals(test1, test2);
    }

    @Test
    void simplifyMultiplyExponents() {
        Function test1 = Parser.parse("(x^3)^2");
        Function test2 = Parser.parse("x^6");
        assertEquals(test1, test2);
    }

    @Test
    void simplifyIdentity() {
        Function test1 = Parser.parse("x+0");
        Function test2 = Parser.parse("x*1");
        assertEquals(test1, test2);
    }

    @Test
    void simplifyPullAdd() {
        Function test1 = Parser.parse("(x+(y+z))");
        Function test2 = Parser.parse("x+y+z");
        assertEquals(test1, test2);
    }

    @Test
    void simplifyPullMultiply() {
        Function test1 = Parser.parse("x*(yz)");
        Function test2 = Parser.parse("x*y*z");
        assertEquals(test1, test2);
    }

    @Test
    void timesZero() {
        Function test1 = Parser.parse("x*0");
        Function test2 = Parser.parse("0");
        assertEquals(test1, test2);
    }
}
