import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.KeywordInterface;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeywordInterfaceTest {

    @Test
    void partialDerivatives() {
        Function test1 = (Function) KeywordInterface.useKeywords("d/dx x^2");
        Function test2 = Parser.parse("2x");
        assertEquals(test2, test1);
    }

    @Test
    void doublePartialDerivatives() {
        Function test1 = (Function) KeywordInterface.useKeywords("d/dx d/dx x^2");
        Function test2 = Parser.parse("2");
        assertEquals(test2, test1);
    }

    @Test
    void partialWithRespectToVariableDoesNotExist() {
        Function test1 = (Function) KeywordInterface.useKeywords("d/dn x^2");
        Function test2 = Parser.parse("0");
        assertEquals(test2, test1);
    }

    @Test
    void partialOfASimp() {
        Function test1 = (Function) KeywordInterface.useKeywords("d/dx simp 2x+3x");
        Function test2 = Parser.parse("5");
        assertEquals(test2, test1);
    }

    @Test
    void partialDerivativeNTimes() {
        Function test1 = (Function) KeywordInterface.useKeywords("pdn x 4 sin(x)");
        Function test2 = Parser.parse("sin(x)");
        assertEquals(test2, test1);
    }

    @Test
    void basicEval() {
        double test = (double) KeywordInterface.useKeywords("eval x^2 2");
        assertEquals(4, test);
    }

    @Test
    void basicEvalWithNewVariable() {
        KeywordInterface.clearvars();
        KeywordInterface.addvars("y");
        double test = (double) KeywordInterface.useKeywords("eval y^2 2");
        assertEquals(4, test);
        KeywordInterface.clearvars();
        KeywordInterface.addvars("x y z");
    }

    @Test
    void basicEvalWithThreeVariables() {
        double test = (double) KeywordInterface.useKeywords("eval y+x+z 3 2 1");
        assertEquals(6, test);
    }

    @Test
    void basicSimplify() {
        Function test1 = (Function) KeywordInterface.useKeywords("simp 1*(x+2x+0)");
        Function test2 = Parser.parse("3x");
        assertEquals(test2, test1);
    }

    @Test
    void notSoBasicSubstitute() {
        KeywordInterface.useKeywords("sto t x^2");
        Function test1 = (Function) KeywordInterface.useKeywords("sub z+y y t");
        Function test2 = Parser.parse("z+x^2");
        assertEquals(test2, test1);
    }

    @Test
    void basicSolve() {
        double[] test = (double[]) KeywordInterface.useKeywords("solve 5-5x 0 2");
        assertArrayEquals(new double[]{1}, test);
    }

    @Test
    void basicExtremaMax() {
        double test = (double) KeywordInterface.useKeywords("extrema max 1-x^2 -pi pi");
        assertEquals(0, test);
    }
}

