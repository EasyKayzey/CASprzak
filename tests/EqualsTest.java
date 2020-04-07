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
}
