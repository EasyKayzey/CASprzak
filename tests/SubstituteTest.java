import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Cos;
import functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.DefaultFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstituteTest {

    @Test
    void simpleSubstitution() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("y^2");
        GeneralFunction test3 = Parser.parse("\\sin(y^2)");
        assertEquals(test1.substituteVariable('x',test2), test3);
    }

    @Test
    void simpleSameVariableSubstitution() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("x^2");
        GeneralFunction test3 = Parser.parse("\\sin(x^2)");
        assertEquals(test1.substituteVariable('x',test2), test3);
    }

    @Test
    void ifVariableIsNotPresentSubstitution() {
        GeneralFunction test1 = Parser.parse("\\sin(x)");
        GeneralFunction test2 = Parser.parse("x^2");
        GeneralFunction test3 = Parser.parse("\\sin(x)");
        assertEquals(test1.substituteVariable('y',test2), test3);
    }

    @Test
    void complexSubstituteAll() {
        GeneralFunction test1 = Parser.parseSimplified("x+\\sin(x)+2\\sin(2x+y)");
        GeneralFunction test2 = Parser.parseSimplified("x+\\cos(2x)+2\\cos(4x+2y)");
        GeneralFunction substituted = test1.substituteAll(f -> f instanceof Sin, f -> new Cos(new Product(DefaultFunctions.TWO, ((UnitaryFunction) f).operand)));
        assertEquals(substituted, test2);
    }
}
