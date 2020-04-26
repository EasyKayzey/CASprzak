import functions.Function;
import org.junit.jupiter.api.Test;
import parsing.KeywordInterface;
import parsing.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeywordInterfaceTest {

    @Test
    void partialDerivatives() {
        Function test1 = (Function) KeywordInterface.useKeywords("d/dx x^2");
        Function test2 = Parser.parse("2x");
        assertEquals(test2, test1);
    }
}

