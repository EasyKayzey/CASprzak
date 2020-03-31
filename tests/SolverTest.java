import CASprzak.Function;
import CASprzak.Parser;
import CASprzak.SingleVariableSolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {
    Parser parserX = new Parser('x');
    Parser parserXY = new Parser('x','y');
    SingleVariableSolver solver = new SingleVariableSolver();

    @Test
    void simplePolynomial() throws Exception {
        Function test = parserX.parse("x ^ 2 + -1");
        assertEquals(1, solver.getSolutionPoint(test, 3));
    }
}
