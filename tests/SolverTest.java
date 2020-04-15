import core.Parser;
import core.SingleVariableSolver;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        Function test = Parser.parse("x^2+-1");
        assertEquals(1, SingleVariableSolver.getSolutionPoint(test, 3));
    }
}
