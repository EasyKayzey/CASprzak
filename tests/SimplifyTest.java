import core.Settings;
import functions.Function;
import functions.commutative.Multiply;
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
