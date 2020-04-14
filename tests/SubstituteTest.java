import core.Parser;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstituteTest {
    final Parser parser = new Parser('x', 'y');

    @Test
    void simpleSubstitution() {
        Function test1 = parser.parse("sin(x)");
        Function test2 = parser.parse("y^2");
        Function test3 = parser.parse("sin(y^2)");
        assertEquals(test1.substitute(0,test2), test3);
    }
}
