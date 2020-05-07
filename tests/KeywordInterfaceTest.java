import config.Settings;
import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.KeywordInterface;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpellCheckingInspection")
public class KeywordInterfaceTest {

    @Test
    void partialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("d/dx x^2");
        GeneralFunction test2 = Parser.parse("2x");
        assertEquals(test2, test1);
    }

    @Test
    void doublePartialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("d/dx d/dx x^2");
        GeneralFunction test2 = Parser.parse("2");
        assertEquals(test2, test1);
    }

    @Test
    void partialWithRespectToVariableDoesNotExist() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("d/dn x^2");
        GeneralFunction test2 = Parser.parse("0");
        assertEquals(test2, test1);
    }

    @Test
    void partialOfASimp() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("d/dx simp 2x+3x");
        GeneralFunction test2 = Parser.parse("5");
        assertEquals(test2, test1);
    }

    @Test
    void partialDerivativeNTimes() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pdn x 4 sin(x)");
        GeneralFunction test2 = Parser.parse("sin(x)");
        assertEquals(test2, test1);
    }

    @Test
    void basicEval() {
        double test = (double) KeywordInterface.useKeywords("eval x^2 x=2");
        assertEquals(4, test);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void basicEvalWithNewVariable() {
        KeywordInterface.useKeywords("clearvars");
        KeywordInterface.useKeywords("addvar y");
        double test = (double) KeywordInterface.useKeywords("eval y^2 y=2");
        assertEquals(4, test);
        KeywordInterface.useKeywords("clearvars");
        KeywordInterface.useKeywords("addvars x y z");
    }

    @Test
    void basicEvalWithThreeVariables() {
        double test = (double) KeywordInterface.useKeywords("eval y+x+z x=3 z=1 y=2");
        assertEquals(6, test);
    }

    @Test
    void basicSimplify() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("simp 1*(x+2x+0)");
        GeneralFunction test2 = Parser.parse("3x");
        assertEquals(test2, test1);
    }

    @Test
    void notSoBasicSubstitute() {
        KeywordInterface.useKeywords("sto t x^2");
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("sub z+y y t");
        GeneralFunction test2 = Parser.parse("z+x^2");
        assertEquals(test2, test1);
    }

    @Test
    void basicSolve() {
        double[] test = (double[]) KeywordInterface.useKeywords("solve 5-5x 0 2");
        assertArrayEquals(new double[]{1}, test, Settings.equalsMargin);
    }

    @Test
    void basicExtremaMax() {
        double test = (double) KeywordInterface.useKeywords("extrema max 1-x^2 -\\pi \\pi");
        assertEquals(0, test);
    }

    @Test
    void basicTaylor() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("tay cos(x) 1 0");
        GeneralFunction test2 = Parser.parse("1");
        assertEquals(test2, test1);
    }

    @Test
    void basicNumericalIntegration() {
        double test = (double) KeywordInterface.useKeywords("intn sin(x) 0 \\pi");
        assertEquals(2, test, 0.01);
    }

    @Test
    void basicNumericalIntegrationWithError() {
        double[] test = (double[]) KeywordInterface.useKeywords("intne sin(x) 0 \\pi");
        assertArrayEquals(new double[]{2, 0.01}, test, 0.01);
    }
}

