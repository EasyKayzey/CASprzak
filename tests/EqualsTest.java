import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.parsing.FunctionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EqualsTest {

    private static final SimplificationSettings settings = DefaultSimplificationSettings.aggressive;

    @Test
    void unitaryEqualsUnitary() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(x)");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void variablesAreDifferent() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(y)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentUnitaryFunctions() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\cos(x)");
        assertNotEquals(test1, test2);
    }

    @Test
    void differentFunctionTypes() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void multiplyAndAdd() {
        GeneralFunction test1 = FunctionParser.parseInfix("x*2");
        GeneralFunction test2 = FunctionParser.parseInfix("x+2");
        assertNotEquals(test1, test2);
    }

    @Test
    void switchedOrderVariableConstant() {
        GeneralFunction test1 = FunctionParser.parseInfix("1+2x");
        GeneralFunction test2 = FunctionParser.parseInfix("2x+1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderVariables() {
        GeneralFunction test1 = FunctionParser.parseInfix("x+y");
        GeneralFunction test2 = FunctionParser.parseInfix("y + x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedVariablesWithPowers() {
        GeneralFunction test1 = FunctionParser.parseInfix("x^2+x^3");
        GeneralFunction test2 = FunctionParser.parseInfix("x^3+x^2");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderVariableMultiply() {
        GeneralFunction test1 = FunctionParser.parseInfix("x+2y");
        GeneralFunction test2 = FunctionParser.parseInfix("2y + x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderMultiplies() {
        GeneralFunction test1 = FunctionParser.parseInfix("3x+2y");
        GeneralFunction test2 = FunctionParser.parseInfix("2y + 3x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderVariablesMul() {
        GeneralFunction test1 = FunctionParser.parseInfix("x*y");
        GeneralFunction test2 = FunctionParser.parseInfix("yx");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedVariablesWithPowersMul() {
        GeneralFunction test1 = FunctionParser.parseInfix("x^2*x^3");
        GeneralFunction test2 = FunctionParser.parseInfix("x^3*x^2");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderVariableMultiplyMul() {
        GeneralFunction test1 = FunctionParser.parseInfix("x*2*y");
        GeneralFunction test2 = FunctionParser.parseInfix("2*y*x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderMultipliesMul() {
        GeneralFunction test1 = FunctionParser.parseInfix("3x*2y");
        GeneralFunction test2 = FunctionParser.parseInfix("2y*3x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderComplicated() {
        GeneralFunction test1 = FunctionParser.parseInfix("x^2+1+y+3\\sin(x)+2x");
        GeneralFunction test2 = FunctionParser.parseInfix("3(\\sin(x))+y+x^2+2x+1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void switchedOrderTrig() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)+\\cos(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\cos(x)+\\sin(x)");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

}
