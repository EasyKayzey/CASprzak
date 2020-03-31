import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTest {
    Parser parser = new Parser('x', 'y');

    @Test
    void unitaryEqualsUnitary() throws Exception {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("sin ( x )");
        assertEquals(test, test1);
    }

    @Test
    void variablesAreDifferent() throws Exception {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("sin ( y )");
        assertNotEquals(test, test1);
    }

    @Test
    void differentUnitaryFunctions() throws Exception {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("cos ( x )");
        assertNotEquals(test, test1);
    }

    @Test
    void differentFunctionTypes() throws Exception {
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("x + 2");
        assertNotEquals(test, test1);
    }


}
