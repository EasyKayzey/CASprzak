import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstituteTest {

    @Test
    void simpleSubstitution() {
        GeneralFunction test1 = Parser.parse("sin(x)");
        GeneralFunction test2 = Parser.parse("y^2");
        GeneralFunction test3 = Parser.parse("sin(y^2)");
        assertEquals(test1.substituteVariable('x',test2), test3);
    }

    @Test
    void simpleSameVariableSubstitution() {
        GeneralFunction test1 = Parser.parse("sin(x)");
        GeneralFunction test2 = Parser.parse("x^2");
        GeneralFunction test3 = Parser.parse("sin(x^2)");
        assertEquals(test1.substituteVariable('x',test2), test3);
    }

    @Test
    void ifVariableIsNotPresentSubstitution() {
        GeneralFunction test1 = Parser.parse("sin(x)");
        GeneralFunction test2 = Parser.parse("x^2");
        GeneralFunction test3 = Parser.parse("sin(x)");
        assertEquals(test1.substituteVariable('y',test2), test3);
    }
}
