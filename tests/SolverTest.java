import parsing.Parser;
import tools.singlevariable.Extrema;
import tools.singlevariable.Solver;
import functions.Function;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SolverTest {

    @Test
    void simplePolynomial() {
        Function test = Parser.parse("x^2+-1");
        assertEquals(1, Solver.getSolutionPoint(test, 3));
    }

    @Test
    void moreAdvancedPolynomial() {
        Function test = Parser.parse("x^4-5x^2+4");
        assertTrue(Arrays.equals(new double[]{-2, -1, 1, 2}, Solver.getSolutionsRange(test, -10, 10)));
    }

    @Test
    void polynomialWithNoSolution() {
        Function test = Parser.parse("x^2+1");
        assertEquals(Double.NaN, Solver.getSolutionPoint(test, 4));
    }

    @Test
    void polynomialWithNoSolutionRange() {
        Function test = Parser.parse("x^2+1");
        assertTrue(Arrays.equals(new double[]{}, Solver.getSolutionsRange(test, -10, 10)));
    }

    @Test
    void simpleNotPolynomial1() {
        Function test = Parser.parse("ln(x)");
        assertTrue(Arrays.equals(new double[]{1}, Solver.getSolutionsRange(test, -10, 10)));
    }

     @Test
    void simpleNotPolynomial2() {
        Function test = Parser.parse("e^(x-5) - 1");
         assertTrue(Arrays.equals(new double[]{5}, Solver.getSolutionsRange(test, 0, 7.68785)));
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
