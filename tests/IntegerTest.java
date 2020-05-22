import functions.GeneralFunction;
import functions.binary.integer.IntegerQuotient;
import functions.binary.integer.Modulo;
import functions.special.Constant;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegerTest {
    @Test
    void basicIntegerQuotient1() {
        GeneralFunction test = new IntegerQuotient(new Constant(7), new Constant(13));
        assertEquals(1, test.evaluate(Map.of()));
    }

    @Test
    void basicIntegerQuotient2() {
        GeneralFunction test = new IntegerQuotient(new Constant(4.999998), new Constant(25.00002));
        assertEquals(5, test.evaluate(Map.of()));
    }

    @Test
    void basicModulo1() {
        GeneralFunction test = new Modulo(new Constant(7), new Constant(13));
        assertEquals(6, test.evaluate(Map.of()));
    }

    @Test
    void basicModulo2() {
        GeneralFunction test = new Modulo(new Constant(7.000067), new Constant(Math.pow(2, 18)));
        assertEquals(1, test.evaluate(Map.of()));
    }


}
