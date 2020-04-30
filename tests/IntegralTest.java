import functions.Function;
import functions.commutative.Add;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.helperclasses.Pair;
import tools.integral.Integral;
import tools.integral.IntegralsTools;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IntegralTest {

    @Test
    void splitAdds() {
        Integral test1 = new Integral(Parser.parse("x+e^x"), 'x');
        Function test2 = new Add(new Integral(Parser.parse("x"), 'x'), new Integral(Parser.parse("e^x"), 'x'));
        assertEquals(test1.integrate() , test2);
    }

    @Test
    void stripConstantsSimple() {
        Function test1 = Parser.parse("3sin(x)");
        Pair<Double, Function> test2 = IntegralsTools.stripConstants(test1);
        assertEquals(3, test2.first);
        assertEquals(Parser.parse("sin(x)"), test2.second);
    }
}
