import functions.GeneralFunction;
import functions.commutative.Product;
import functions.unitary.UnitaryFunction;
import functions.unitary.trig.normal.Cos;
import functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.DefaultFunctions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstituteTest {

    @Test
    void simpleSubstitution() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("y^2");
        GeneralFunction test3 = FunctionParser.parseInfix("\\sin(y^2)");
        assertEquals(test1.substituteVariable(Map.of("x", test2)), test3);
    }

    @Test
    void simpleSameVariableSubstitution() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("x^2");
        GeneralFunction test3 = FunctionParser.parseInfix("\\sin(x^2)");
        assertEquals(test1.substituteVariable(Map.of("x", test2)), test3);
    }

    @Test
    void ifVariableIsNotPresentSubstitution() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("x^2");
        GeneralFunction test3 = FunctionParser.parseInfix("\\sin(x)");
        assertEquals(test1.substituteVariable(Map.of("y", test2)), test3);
    }

    @Test
    void complexSubstituteAll() {
        GeneralFunction test1 = FunctionParser.parseSimplified("x+\\sin(x)+2\\sin(2x+y)");
        GeneralFunction test2 = FunctionParser.parseSimplified("x+\\cos(2x)+2\\cos(4x+2y)");
        GeneralFunction substituted = test1.substituteAll(f -> f instanceof Sin, f -> new Cos(new Product(DefaultFunctions.TWO, ((UnitaryFunction) f).operand)));
        assertEquals(substituted, test2);
    }
}
