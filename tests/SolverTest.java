import core.Parser;
import core.SingleVariableSolver;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {
    final Parser parserX = new Parser('x');
    final SingleVariableSolver solver = new SingleVariableSolver();

    @Test
    void simplePolynomial() {
        Function test = parserX.parse("x^2+-1");
        assertEquals(1, solver.getSolutionPoint(test, 3));
    }
}
