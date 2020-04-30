import functions.Function;
import functions.commutative.CommutativeFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.DefaultFunctions;
import tools.SearchTools;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void basicSearch() {
        Function test1 = Parser.parse("sin(x+1)");
        Function test2 = Parser.parse("x");
        assertTrue(SearchTools.exists(test1, f -> f.equals(test2)));
    }

    @Test
    void searchExcluding() {
        Function test1 = Parser.parse("sin(x+1)");
        Function test2 = Parser.parse("x");
        Function test3 = Parser.parse("x+1");
        assertFalse(SearchTools.existsExcluding(test1, f -> f.equals(test2), f -> f.equals(test3)));
    }

    @Test
    void surfaceSubset() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("a+b+sin(x+1)");
        Function test2 = Parser.parseSimplified("a+sin(x+1)");
        assertTrue(SearchTools.existsInSurfaceSubset(test1, f -> f.equals(test2)));
    }

    @Test
    void simpleSubsetNoExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        Function test2 = Parser.parseSimplified("x*e^x");
        assertTrue(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> f.equals(DefaultFunctions.X)), (f -> f.equals(test2))));
    }

    @Test
    void simpleSubsetExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        Function test2 = Parser.parseSimplified("x*e^x");
        Function test3 = Parser.parseSimplified("sin x");
        assertFalse(SearchTools.existsInOppositeSurfaceSubsetExcluding(test1, (f -> f.equals(DefaultFunctions.X)), (f -> f.equals(test2)), (f -> f.equals(test3))));
    }
}
