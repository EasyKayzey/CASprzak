import parsing.Parser;
import tools.singlevariable.Extrema;
import tools.singlevariable.Solver;
import functions.Function;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        Function test = Parser.parse("x^2+-1");
        assertEquals(1, Solver.getSolutionPoint(test, 3));
    }

    @Test
    void simpleMinima() {
        Function test = Parser.parse("x^2-1");
        assertEquals(0, Extrema.findLocalMinima(test, -3,3));
    }

    @Test
    void lessSimpleMinima() {
        Function test = Parser.parse("x^2-6x+8");
        assertEquals(3, Extrema.findLocalMinima(test, -5,5));
    }
}
