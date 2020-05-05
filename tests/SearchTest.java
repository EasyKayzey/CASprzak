import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import org.junit.jupiter.api.Test;
import parsing.Parser;
import tools.SearchTools;
import tools.VariableTools;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void basicSearch() {
        GeneralFunction test1 = Parser.parse("sin(x+1)");
        GeneralFunction test2 = Parser.parse("x");
        assertTrue(SearchTools.existsAny(test1, test2::equalsFunction));
    }

    @Test
    void searchExcluding() {
        GeneralFunction test1 = Parser.parse("sin(x+1)");
        GeneralFunction test2 = Parser.parse("x");
        GeneralFunction test3 = Parser.parse("x+1");
        assertFalse(SearchTools.existsExcluding(test1, test2::equalsFunction, test3::equalsFunction));
    }

    @Test
    void searchSurface() {
        GeneralFunction test1 = Parser.parse("2sin(x+1)");
        GeneralFunction test2 = Parser.parse("x+1");
        GeneralFunction test3 = Parser.parse("sin(x+1)");
        assertFalse(SearchTools.existsSurface(test1, test2::equalsFunction));
        assertTrue(SearchTools.existsSurface(test1, test3::equalsFunction));
    }

    @Test
    void surfaceSubset() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("a+b+sin(x+1)");
        GeneralFunction test2 = Parser.parseSimplified("a+sin(x+1)");
        assertTrue(SearchTools.existsInSurfaceSubset(test1, test2::equalsFunction));
    }

    @Test
    void simpleSubsetNoExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        GeneralFunction test2 = Parser.parseSimplified("x*e^x");
        assertTrue(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.existsAny(f, VariableTools.isVariable('x'))), test2::equalsFunction));
    }

    @Test
    void simpleSubsetExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) Parser.parseSimplified("x*sin(x)*e^x");
        GeneralFunction test2 = Parser.parseSimplified("x*e^x");
        GeneralFunction test3 = Parser.parseSimplified("sin(x)");
        assertFalse(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.existsExcluding(f, VariableTools.isVariable('x'), test3::equalsFunction)), test2::equalsFunction));
    }

    @Test
    void isVariable() {
        assertTrue(VariableTools.isVariable('y').test(Parser.parseSimplified("y")));
        assertFalse(VariableTools.isVariable('x').test(Parser.parseSimplified("y")));
        assertFalse(VariableTools.isVariable('x').test(Parser.parseSimplified("x+1")));
        assertFalse(VariableTools.isVariable('x').test(Parser.parseSimplified("2x")));
    }

    @Test
    void findVariables() {
        GeneralFunction test = Parser.parseSimplified("x+2a+3pi^2-17x");
        assertEquals(VariableTools.getAllVariables(test), Set.of('a', 'x'));
    }
}
