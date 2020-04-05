import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTest {
    final Parser parser = new Parser('x', 'y');

    @Test
    void unitaryEqualsUnitary() {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("sin ( x )");
        assertEquals(test, test1);
    }

    @Test
    void variablesAreDifferent() {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("sin ( y )");
        assertNotEquals(test, test1);
    }

    @Test
    void differentUnitaryFunctions() {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("cos ( x )");
        assertNotEquals(test, test1);
    }

    @Test
    void differentFunctionTypes() {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("x + 2");
        assertNotEquals(test, test1);
    }


}
