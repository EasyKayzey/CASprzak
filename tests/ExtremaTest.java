import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.singlevariable.Extrema;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExtremaTest {
    @Test
    void simpleMinima() {
        Function test = Parser.parse("x^2-1");
        assertEquals(0, Extrema.findLocalMinimum(test, -3,3));
    }

    @Test
    void lessSimpleMinima() {
        Function test = Parser.parse("x^2-6x+8");
        assertEquals(3, Extrema.findLocalMinimum(test, -5,5));
    }

    @Test
    void simpleMaxima() {
        Function test = Parser.parse("1-x^2");
        assertEquals(0, Extrema.findLocalMaximum(test, -4, 3));
    }

    @Test
    void simpleHighOrderMinima() {
        Function test = Parser.parse("(x-1)^4");
        assertArrayEquals(new double[]{1}, Extrema.findAnyMinima(test, -5.87329472, 5.023954780232345), 0.01);
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
        assertEquals(Double.NaN, Extrema.findLocalMinimum(test, -7.62184632046246723904723482376450934, 4.788237402784035873405));
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

    @Test
    void sinTest() {
        Function test = Parser.parse("sin(x)");
        assertEquals(Math.PI/2, Extrema.findLocalMaximum(test, 0, Math.PI), .01);
    }

    @Test
    void minOnRange() {
        Function test = Parser.parse("(x-1)^2+3");
        assertEquals(1, Extrema.findMinimumOnRange(test, -3, 17));
    }

    @Test
    void minOnRangeBoundary() {
        Function test = Parser.parse("x");
        assertEquals(3, Extrema.findMinimumOnRange(test, 3, 17));
    }

    @Test
    void maxOnRange() {
        Function test = Parser.parse("3-(x+2)^2");
        assertEquals(-2, Extrema.findMaximumOnRange(test, -3, 13));
    }

    @Test
    void maxOnRangeBoundary() {
        Function test = Parser.parse("x");
        assertEquals(11, Extrema.findMaximumOnRange(test, 3, 11));
    }
}
