import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Rand;
import show.ezkz.casprzak.core.functions.binary.integer.IntegerQuotient;
import show.ezkz.casprzak.core.functions.binary.integer.Modulo;
import show.ezkz.casprzak.core.functions.commutative.integer.GCD;
import show.ezkz.casprzak.core.functions.commutative.integer.LCM;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.functions.unitary.piecewise.Ceil;
import show.ezkz.casprzak.core.functions.unitary.piecewise.Floor;
import show.ezkz.casprzak.core.functions.unitary.piecewise.Round;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IntegerTest {
    @Test
    void basicIntegerQuotient1() {
        GeneralFunction test = new IntegerQuotient(new Constant(7), new Constant(13));
        assertEquals(1, test.evaluate());
    }

    @Test
    void basicIntegerQuotient2() {
        GeneralFunction test = new IntegerQuotient(new Constant(4.999999999999999999999999999999999999999999999998), new Constant(25.000000000000000000000000002));
        assertEquals(5, test.evaluate());
    }

    @Test
    void basicModulo1() {
        GeneralFunction test = new Modulo(new Constant(7), new Constant(13));
        assertEquals(6, test.evaluate());
    }

    @Test
    void basicModulo2() {
        GeneralFunction test = new Modulo(new Constant(7.0000000000000000000000000000000000000000000000000067), new Constant(Math.pow(2, 18)));
        assertEquals(1, test.evaluate());
    }

    @Test
    void gcd() {
        assertEquals(new Constant(4), new GCD(new Constant(4), new Constant(196), new Constant(80)).simplify());
        assertEquals(4, new GCD(new Constant(4), new Constant(196), new Constant(80)).evaluate());
        assertEquals(4, new GCD(new Constant(4), new Constant(-196), new Constant(80)).evaluate());
    }

    @Test
    void lcm() {
        assertEquals(new Constant(3920), new LCM(new Constant(4), new Constant(196), new Constant(80)).simplify());
        assertEquals(3920, new LCM(new Constant(4), new Constant(196), new Constant(80)).evaluate());
        assertEquals(-3920, new LCM(new Constant(4), new Constant(-196), new Constant(-80)).evaluate());
    }

    @Test
    void basicFloor() {
        GeneralFunction test = new Floor(new Sin(new Variable("x")));
        assertEquals(0, test.evaluate(Map.of("x", 1.0)));
    }

    @Test
    void basicCeil() {
        GeneralFunction test = new Ceil(new Sin(new Variable("x")));
        assertEquals(1, test.evaluate(Map.of("x", 1.0)));
    }

    @Test
    void basicRound() {
        GeneralFunction test = new Round(new Sin(new Variable("x")));
        assertEquals(1, test.evaluate(Map.of("x", 1.0)));
    }

    @Test
    void basicRand() {
        GeneralFunction test = new Rand(new Constant(0.0), new Constant(10.0));
        assertNotEquals(test.evaluate(), test.evaluate());
    }



}
