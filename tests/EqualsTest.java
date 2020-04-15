import core.Parser;
import core.Settings;
import functions.Function;
import functions.commutative.Multiply;
import org.junit.jupiter.api.Test;

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
    void equalWhenSimplified() {
        Function test1 = Parser.parse("x+(1+3-2)*1");
        Function test2 = Parser.parse("x+2");
        assertEquals(test1, test2);
    }

    @Test
    void constantEquality() {
        Function test1, test2;
        test1 = Parser.parse("1");
        test2 = Parser.parse("0+1");
        assertEquals(test1, test2);
        test1 = Parser.parse("1");
        test2 = Parser.parse("2");
        assertNotEquals(test1, test2);
        test1 = Parser.parse("e");
        test2 = Parser.parse("" + Math.E);
        assertEquals(test1, test2);
    }

    @Test
    void simplifiesDivisionExponents() {
        Function test1 = Parser.parse("x^3/x^2");
        Function test2 = Parser.parse("x");
        assertEquals(test1, test2);
    }

    @Test
    void combineLikeTerms() {
        Function test1 = Parser.parse("3*x^2+5*x^-1+7*x^-1-3*x^2+1");
        Function test2 = Parser.parse("1+12*x^-1");
        assertEquals(test1, test2);
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
//        System.out.println(test1.simplify());
//        System.out.println(test2.simplify());
        assertEquals(test1, test2);
    }


    @Test
    void distributeTerms() {
        Function test1 = Parser.parse("sin(x)*(1+5x)");
        Function test2 = Parser.parse("sin(x)+5*x*sin(x)");
//        System.out.println(((Multiply)test1).distributeAll().simplify());
//        System.out.println(test2.simplify());
        assertEquals(((Multiply)test1).distributeAll(), test2);
    }

    @Test
    void distributeExponents() {
        Settings.distributeExponents = true;
        Function test1 = Parser.parse("(2xy)^2");
        Function test2 = Parser.parse("4x^2y^2");
//        System.out.println(test1.simplify());
//        System.out.println(test2.simplify());
        assertEquals(test1, test2);
    }

    @Test
    void addExponents() {
        Function test1 = Parser.parse("x^2*x^4");
        Function test2 = Parser.parse("x^6");
        assertEquals(test1, test2);
    }
}
