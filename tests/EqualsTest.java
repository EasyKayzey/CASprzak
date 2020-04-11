import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTest {
    final Parser parser = new Parser('x', 'y');

    @Test
    void unitaryEqualsUnitary() {
        Function test1 = parser.parse("sin(x)");
        Function test2 = parser.parse("sin(x)");
        assertEquals(test1, test2);
    }

    @Test
    void variablesAreDifferent() {
        Function test1 = parser.parse("sin(x)");
        Function test2 = parser.parse("sin(y)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentUnitaryFunctions() {
        Function test1 = parser.parse("sin(x)");
        Function test2 = parser.parse("cos(x)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentFunctionTypes() {
        Function test1 = parser.parse("sin(x)");
        Function test2 = parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void multiplyAndAdd() {
        Function test1 = parser.parse("x*2");
        Function test2 = parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void equalWhenSimplified() {
        Function test1 = parser.parse("x+(1+3-2)*1");
        Function test2 = parser.parse("x+2");
        assertEquals(test1, test2);
    }

    @Test
    void constantEquality() {
        Function test1, test2;
        test1 = parser.parse("1");
        test2 = parser.parse("0+1");
        assertEquals(test1, test2);
        test1 = parser.parse("1");
        test2 = parser.parse("2");
        assertNotEquals(test1, test2);
        test1 = parser.parse("e");
        test2 = parser.parse("" + Math.E);
        assertEquals(test1, test2);
    }

    @Test
    void simplifiesDivisionExponents() {
        Function test1 = parser.parse("x^3/x^2");
        Function test2 = parser.parse("x");
        assertEquals(test1, test2);
    }

    @Test
    void combineLikeTerms() {
        Function test1 = parser.parse("3*x^2+5*x^-1+7*x^-1-3*x^2+1");
        Function test2 = parser.parse("1+12*x^-1");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableConstant() {
        Function test1 = parser.parse("1+2x");
        Function test2 = parser.parse("2x+1");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariables() {
        Function test1 = parser.parse("x+y");
        Function test2 = parser.parse("y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedVariablesWithPowers() {
        Function test1 = parser.parse("x^2+x^3");
        Function test2 = parser.parse("x^3+x^2");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableMultiply() {
        Function test1 = parser.parse("x+2y");
        Function test2 = parser.parse("2y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderMultiplies() {
        Function test1 = parser.parse("3x+2y");
        Function test2 = parser.parse("2y + 3x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderComplicated() {
        Function test1 = parser.parse("x^2+1+y+3sin(x)+2x");
        Function test2 = parser.parse("3(sin(x))+y+x^2+2x+1");
//        System.out.println(test1.simplify());
//        System.out.println(test2.simplify());
        assertEquals(test1, test2);
    }


    @Test
    void distributeTerms() {
        Function test1 = parser.parse("sin(x)*(1+5x)");
        Function test2 = parser.parse("sin(x)+5*x*sin(x)");
        System.out.println(((Multiply)test1).distributeAll().simplify());
        System.out.println(test2.simplify());
        assertEquals(((Multiply)test1).distributeAll(), test2);
    }
}
