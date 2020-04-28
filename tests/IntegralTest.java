import functions.Function;
import functions.commutative.Add;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.integral.Integral;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IntegralTest {

    @Test
    void splitAdds() {
        Integral test1 = new Integral(Parser.parse("x+e^x"), 'x');
        Function test2 = new Add(new Integral(Parser.parse("x"), 'x'), new Integral(Parser.parse("e^x"), 'x'));
        assertEquals(test1.integrate() , test2);
    }
}
