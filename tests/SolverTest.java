import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.singlevariable.Extrema;
import tools.singlevariable.Solver;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        Function test = Parser.parse("x^2+-1");
        assertEquals(1, Solver.getSolutionPoint(test, 3));
    }

    @Test
    void moreAdvancedPolynomial() {
        Function test = Parser.parse("x^4-5x^2+4");
        assertArrayEquals(new double[]{-2, -1, 1, 2}, Solver.getSolutionsRange(test, -10, 10));
    }

    @Test
    void polynomialWithNoSolution() {
        Function test = Parser.parse("x^2+1");
        assertEquals(Double.NaN, Solver.getSolutionPoint(test, 4));
    }

    @Test
    void polynomialWithNoSolutionRange() {
        Function test = Parser.parse("x^2+1");
        assertArrayEquals(new double[]{}, Solver.getSolutionsRange(test, -10, 10));
    }

    @Test
    void simpleNotPolynomial1() {
        Function test = Parser.parse("ln(x)");
        assertArrayEquals(new double[]{1}, Solver.getSolutionsRange(test, -10, 10));
    }

     @Test
    void simpleNotPolynomial2() {
        Function test = Parser.parse("e^(x-5) - 1");
         assertArrayEquals(new double[]{5}, Solver.getSolutionsRange(test, 0, 7.68785));
    }

    @Test
    void simpleTrigZero() {
        Function test = Parser.parse("sin(x-3)");
        assertEquals(3, Solver.getSolutionPoint(test, 3.5));
    }

    @Test
    void simpleConstant() {
        Function test = Parser.parse("2");
        assertEquals(Double.NaN, Solver.getSolutionPoint(test, 23));
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

    @Test
    void simpleMaxima() {
        Function test = Parser.parse("1-x^2");
        assertEquals(0, Extrema.findLocalMaxima(test, -4, 3));
    }

    @Test
    void simpleInflection() {
        Function test = Parser.parse("x^3");
        assertArrayEquals(new double[]{0}, Extrema.findAnyInflectionPoints(test, -5.87329472, 5.023954780232345));
    }

    @Test
    void simpleInflectionWithNoInflection() {
        Function test = Parser.parse("x^2");
        assertArrayEquals(new double[]{}, Extrema.findAnyInflectionPoints(test, -5.01238941, 7.80293154));
    }

    @Test
    void simpleMinimaWithNoMinima() {
        Function test = Parser.parse("1-x^2");
        assertEquals(Double.NaN, Extrema.findLocalMinima(test, -7.62184632046246723904723482376450934, 4.788237402784035873405));
    }

    @Test
    void simpleFindAnyMinima() {
        Function test = Parser.parse("x^{4}-3x^{3}+2x^{2}+x+1");
        assertArrayEquals(new double[]{-.175, 1.425}, Extrema.findAnyMinima(test, -6.30457892, 7.2543525), 0.01);
    }

    @Test
    void simpleFindAnyMaxima() {
        Function test = Parser.parse("x^{4}-3x^{3}+2x^{2}+x+1");
        assertArrayEquals(new double[]{1}, Extrema.findAnyMaxima(test, -6.30457892, 7.2543525), .01);
    }
}
