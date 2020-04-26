import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SimplifyTest {

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
}
