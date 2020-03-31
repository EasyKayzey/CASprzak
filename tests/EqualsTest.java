import CASprzak.Function;
import CASprzak.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EqualsTest {

        @Test
        void unitaryEqualsUnitary() throws Exception {
            Parser parser = new Parser('x');
            Function test = parser.parse("sin ( x )");
            Function test1 = parser.parse("sin ( x )");
            assertTrue(test.equals(test1));
        }

        @Test void variablesAreDifferent() throws Exception {
            Parser parser = new Parser('x','y');
            Function test = parser.parse("sin ( x )");
            Function test1 = parser.parse("sin ( y )");
            assertFalse(test.equals(test1));
        }

     @Test void differentUnitaryFunctions() throws Exception {
        Parser parser = new Parser('x');
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("cos ( x )");
        assertFalse(test.equals(test1));
    }

    @Test void differentFunctionTypes() throws Exception {
        Parser parser = new Parser('x');
        Function test = parser.parse("sin ( x )");
        Function test1 = parser.parse("x + 2");
        assertFalse(test.equals(test1));
    }





}
