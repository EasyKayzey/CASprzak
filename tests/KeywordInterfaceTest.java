import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.KeywordInterface;
import parsing.Parser;

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
}

