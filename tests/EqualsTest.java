import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EqualsTest {

    @Test
    void unitaryEqualsUnitary() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("\\sin(x)");
        assertEquals(test1, test2);
    }

    @Test
    void variablesAreDifferent() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("\\sin(y)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentUnitaryFunctions() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("\\cos(x)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentFunctionTypes() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void multiplyAndAdd() {
        GeneralFunction test1 = Parser.parse("x*2");
        GeneralFunction test2 = Parser.parse("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableConstant() {
        GeneralFunction test1 = Parser.parse("1+2x");
        GeneralFunction test2 = Parser.parse("2x+1");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariables() {
        GeneralFunction test1 = Parser.parse("x+y");
        GeneralFunction test2 = Parser.parse("y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedVariablesWithPowers() {
        GeneralFunction test1 = Parser.parse("x^2+x^3");
        GeneralFunction test2 = Parser.parse("x^3+x^2");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableMultiply() {
        GeneralFunction test1 = Parser.parse("x+2y");
        GeneralFunction test2 = Parser.parse("2y + x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderMultiplies() {
        GeneralFunction test1 = Parser.parse("3x+2y");
        GeneralFunction test2 = Parser.parse("2y + 3x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariablesMul() {
        GeneralFunction test1 = Parser.parse("x*y");
        GeneralFunction test2 = Parser.parse("yx");
        assertEquals(test1, test2);
    }

    @Test
    void switchedVariablesWithPowersMul() {
        GeneralFunction test1 = Parser.parse("x^2*x^3");
        GeneralFunction test2 = Parser.parse("x^3*x^2");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableMultiplyMul() {
        GeneralFunction test1 = Parser.parse("x*2*y");
        GeneralFunction test2 = Parser.parse("2*y*x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderMultipliesMul() {
        GeneralFunction test1 = Parser.parse("3x*2y");
        GeneralFunction test2 = Parser.parse("2y*3x");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderComplicated() {
        GeneralFunction test1 = Parser.parse("x^2+1+y+3\\sin(x)+2x");
        GeneralFunction test2 = Parser.parse("3(\\sin(x))+y+x^2+2x+1");
        assertEquals(test1, test2);
    }

    @Test
    void switchedOrderTrig() {
        GeneralFunction test1 = Parser.parse("\\sin(x)+\\cos(x)");
        GeneralFunction test2 = Parser.parse("\\cos(x)+\\sin(x)");
        assertEquals(test1, test2);
    }

}
