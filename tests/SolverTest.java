import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.singlevariable.Solver;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        GeneralFunction test = Parser.parse("x^2+-1");
        assertEquals(1, Solver.getSolutionPointNewton(test, 3));
    }

    @Test
    void moreAdvancedPolynomial() {
        GeneralFunction test = Parser.parse("x^4-5x^2+4");
        assertArrayEquals(new double[]{-2, -1, 1, 2}, Solver.getSolutionsRangeNewton(test, -10, 10), 1e-10);
    }

    @Test
    void polynomialWithNoSolution() {
        GeneralFunction test = Parser.parse("x^2+1");
        assertEquals(Double.NaN, Solver.getSolutionPointNewton(test, 4));
    }

    @Test
    void polynomialWithNoSolutionRange() {
        GeneralFunction test = Parser.parse("x^2+1");
        assertArrayEquals(new double[]{}, Solver.getSolutionsRangeNewton(test, -10, 10));
    }

    @Test
    void simpleNotPolynomial1() {
        GeneralFunction test = Parser.parse("\\ln(x)");
        assertArrayEquals(new double[]{1}, Solver.getSolutionsRangeNewton(test, -10, 10));
    }

     @Test
    void simpleNotPolynomial2() {
        GeneralFunction test = Parser.parse("e^(x-5) - 1");
         assertArrayEquals(new double[]{5}, Solver.getSolutionsRangeHalley(test, 0, 7.68785));
    }

    @Test
    void simpleTrigZero() {
        GeneralFunction test = Parser.parse("\\sin(x-3)");
        assertEquals(3, Solver.getSolutionPointNewton(test, 3.5));
    }

    @Test
    void simpleConstant() {
        GeneralFunction test = Parser.parse("2");
        assertEquals(Double.NaN, Solver.getSolutionPointNewton(test, 23));
    }

    @Test
    void simpleExponent() {
        GeneralFunction test = Parser.parse("(x+1)^2");
        assertEquals(-1, Solver.getSolutionPointNewton(test, 23), 1e-6);
    }

    @Test void chemTest1() {
        GeneralFunction test = Parser.parse("x^2/(.2-x)-.013");
        assertArrayEquals(new double[]{-.057, .045}, Solver.getSolutionsRangeHalley(test, -10, 10), .05);
    }

    @Test void chemTest2() {
        GeneralFunction test = Parser.parse("(.12x)^2/((1-x).12)-6.3E-5");
        assertEquals(1, Solver.getSolutionsRangeHalley(test, 0, 1).length);
    }
}
