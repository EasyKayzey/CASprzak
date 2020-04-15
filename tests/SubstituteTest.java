import parsing.Parser;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstituteTest {

    @Test
    void simpleSubstitution() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("y^2");
        Function test3 = Parser.parse("sin(y^2)");
        assertEquals(test1.substitute(0,test2), test3);
    }

    @Test
    void simpleSameVariableSubstitution() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("x^2");
        Function test3 = Parser.parse("sin(x^2)");
        assertEquals(test1.substitute(0,test2), test3);
    }

    @Test
    void ifVariableIsNotPresentSubstitution() {
        Function test1 = Parser.parse("sin(x)");
        Function test2 = Parser.parse("x^2");
        Function test3 = Parser.parse("sin(x)");
        assertEquals(test1.substitute(1,test2), test3);
    }
}
