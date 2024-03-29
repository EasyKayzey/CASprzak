import core.functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import core.tools.singlevariable.Solver;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        GeneralFunction test = FunctionParser.parseInfix("x^2+-1");
        assertEquals(1, Solver.getSolutionPointNewton(test, 3));
    }

    @Test
    void moreAdvancedPolynomial() {
        GeneralFunction test = FunctionParser.parseInfix("x^4-5x^2+4");
        assertArrayEquals(new double[]{-2, -1, 1, 2}, Solver.getSolutionsRangeNewton(test, -10, 10).stream().mapToDouble(i -> i).toArray(), 1e-10);
    }

    @Test
    void polynomialWithNoSolution() {
        GeneralFunction test = FunctionParser.parseInfix("x^2+1");
        assertEquals(Double.NaN, Solver.getSolutionPointNewton(test, 4));
    }

    @Test
    void polynomialWithNoSolutionRange() {
        GeneralFunction test = FunctionParser.parseInfix("x^2+1");
        assertArrayEquals(new double[]{}, Solver.getSolutionsRangeNewton(test, -10, 10).stream().mapToDouble(i -> i).toArray());
    }

    @Test
    void simpleNotPolynomial1() {
        GeneralFunction test = FunctionParser.parseInfix("\\ln(x)");
        assertArrayEquals(new double[]{1}, Solver.getSolutionsRangeNewton(test, -10, 10).stream().mapToDouble(i -> i).toArray());
    }

     @Test
    void simpleNotPolynomial2() {
        GeneralFunction test = FunctionParser.parseInfix("e^(x-5) - 1");
         assertArrayEquals(new double[]{5}, Solver.getSolutionsRangeHalley(test, 0, 7.68785).stream().mapToDouble(i -> i).toArray());
    }

    @Test
    void simpleTrigZero() {
        GeneralFunction test = FunctionParser.parseInfix("\\sin(x-3)");
        assertEquals(3, Solver.getSolutionPointNewton(test, 3.5));
    }

    @Test
    void simpleConstant() {
        GeneralFunction test = FunctionParser.parseInfix("2");
        assertEquals(Double.NaN, Solver.getSolutionPointNewton(test, 23));
    }

    @Test
    void simpleExponent() {
        GeneralFunction test = FunctionParser.parseInfix("(x+1)^2");
        assertEquals(-1, Solver.getSolutionPointNewton(test, 23), 1e-6);
    }

    @Test void chemTest1() {
        GeneralFunction test = FunctionParser.parseInfix("x^2/(.2-x)-.013");
        assertArrayEquals(new double[]{-.057, .045}, Solver.getSolutionsRangeHalley(test, -10, 10).stream().mapToDouble(i -> i).toArray(), .05);
    }

    @Test void chemTest2() {
        GeneralFunction test = FunctionParser.parseInfix("(.12x)^2/((1-x).12)-6.3E-5");
        assertEquals(1, Solver.getSolutionsRangeHalley(test, 0, 1).size());
    }

    @Test void zeroAtZero() {
        GeneralFunction test = FunctionParser.parseSimplified("2x^2+x");
        assertArrayEquals(new double[]{-.5, 0}, Solver.getSolutionsRange(test, -10, 10).stream().mapToDouble(i -> i).toArray(), .01);
    }

    @Test void xToFour() {
        GeneralFunction test = FunctionParser.parseSimplified("x^4");
        assertArrayEquals(new double[]{0}, Solver.getSolutionsRange(test, -10, 10).stream().mapToDouble(i -> i).toArray());
    }
}
