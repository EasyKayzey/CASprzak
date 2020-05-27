import functions.GeneralFunction;
import functions.commutative.CommutativeFunction;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import tools.SearchTools;
import tools.VariableTools;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest {

    @Test
    void basicSearch() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x+1)");
        GeneralFunction test2 = FunctionParser.parseInfix("x");
        assertTrue(SearchTools.existsAny(test1, test2::equalsFunction));
    }

    @Test
    void searchExcluding() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x+1)");
        GeneralFunction test2 = FunctionParser.parseInfix("x");
        GeneralFunction test3 = FunctionParser.parseInfix("x+1");
        assertFalse(SearchTools.existsExcluding(test1, test2::equalsFunction, test3::equalsFunction));
    }

    @Test
    void searchSurface() {
        GeneralFunction test1 = FunctionParser.parseInfix("2\\sin(x+1)");
        GeneralFunction test2 = FunctionParser.parseInfix("x+1");
        GeneralFunction test3 = FunctionParser.parseInfix("\\sin(x+1)");
        assertFalse(SearchTools.existsSurface(test1, test2::equalsFunction));
        assertTrue(SearchTools.existsSurface(test1, test3::equalsFunction));
    }

    @Test
    void surfaceSubset() {
        CommutativeFunction test1 = (CommutativeFunction) FunctionParser.parseSimplified("a+b+\\sin(x+1)");
        GeneralFunction test2 = FunctionParser.parseSimplified("a+\\sin(x+1)");
        assertTrue(SearchTools.existsInSurfaceSubset(test1, test2::equalsFunction));
    }

    @Test
    void simpleSubsetNoExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) FunctionParser.parseSimplified("x*\\sin(x)*e^x");
        GeneralFunction test2 = FunctionParser.parseSimplified("x*e^x");
        assertTrue(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.existsAny(f, VariableTools.isVariable("x"))), test2::equalsFunction));
    }

    @Test
    void simpleSubsetExclusion() {
        CommutativeFunction test1 = (CommutativeFunction) FunctionParser.parseSimplified("x*\\sin(x)*e^x");
        GeneralFunction test2 = FunctionParser.parseSimplified("x*e^x");
        GeneralFunction test3 = FunctionParser.parseSimplified("\\sin(x)");
        assertFalse(SearchTools.existsInOppositeSurfaceSubset(test1, (f -> SearchTools.existsExcluding(f, VariableTools.isVariable("x"), test3::equalsFunction)), test2::equalsFunction));
    }

    @Test
    void isVariable() {
        assertTrue(VariableTools.isVariable("y").test(FunctionParser.parseSimplified("y")));
        assertFalse(VariableTools.isVariable("x").test(FunctionParser.parseSimplified("y")));
        assertFalse(VariableTools.isVariable("x").test(FunctionParser.parseSimplified("x+1")));
        assertFalse(VariableTools.isVariable("x").test(FunctionParser.parseSimplified("2x")));
    }

    @Test
    void findVariables() {
        GeneralFunction test = FunctionParser.parseSimplified("x+2a+3\\pi^2-17x");
        assertEquals(VariableTools.getAllVariables(test), Set.of("a", "x"));
    }
}
