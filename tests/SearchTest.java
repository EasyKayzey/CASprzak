import functions.Function;
import functions.commutative.CommutativeFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.SearchTools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest {

    @Test
    void basicSearch() {
        Function test1 = Parser.parse("sin(x+1)");
        Function test2 = Parser.parse("x");
        assertTrue(SearchTools.exists(test1, test2::equalsFunction));
    }

    @Test
    void searchExcluding() {
        Function test1 = Parser.parse("sin(x+1)");
        Function test2 = Parser.parse("x");
        Function test3 = Parser.parse("x+1");
        assertFalse(SearchTools.existsExcluding(test1, test2::equalsFunction, test3::equalsFunction));
    }

    @Test
    void searchSurface() {
        Function test1 = Parser.parse("2sin(x+1)");
        Function test2 = Parser.parse("x+1");
        Function test3 = Parser.parse("sin(x+1)");
        assertFalse(SearchTools.existsSurface(test1, test2::equalsFunction));
        assertTrue(SearchTools.existsSurface(test1, test3::equalsFunction));
    }

    @Test
    void surfaceSubset() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("a+b+sin(x+1)");
        Function test2 = Parser.parseSimplified("a+sin(x+1)");
        assertTrue(SearchTools.existsInSurfaceSubset(test1, test2::equalsFunction));
    }

    @Test
    void simpleSubsetNoExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        Function test2 = Parser.parseSimplified("x*e^x");
        assertTrue(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.exists(f, SearchTools.isVariable('x'))), test2::equalsFunction));
    }

    @Test
    void simpleSubsetExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        Function test2 = Parser.parseSimplified("x*e^x");
        Function test3 = Parser.parseSimplified("sin(x)");
        assertFalse(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.existsExcluding(f, SearchTools.isVariable('x'), test3::equalsFunction)), test2::equalsFunction));
    }

    @Test
    void isVariable() {
        assertTrue(SearchTools.isVariable('y').test(Parser.parseSimplified("y")));
        assertFalse(SearchTools.isVariable('x').test(Parser.parseSimplified("y")));
        assertFalse(SearchTools.isVariable('x').test(Parser.parseSimplified("x+1")));
        assertFalse(SearchTools.isVariable('x').test(Parser.parseSimplified("2x")));
    }
}
