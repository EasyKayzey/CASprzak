import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Cos;
import show.ezkz.casprzak.core.functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.commandui.KeywordInterface;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"SpellCheckingInspection", "unchecked"})
public class KeywordInterfaceTest {

    private static final SimplificationSettings settings = DefaultSimplificationSettings.user;

    @Test
    void partialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("pd x x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("2x");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void doublePartialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("pd x pd x x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("2");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void partialWithRespectToVariableDoesNotExist() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("pd n x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("0");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void partialOfASimp() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("pd x simp 2x+3x");
        GeneralFunction test2 = FunctionParser.parseInfix("5");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void partialDerivativeNTimes() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("pdn x 4 \\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(x)");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void basicEval() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("eval x^2 x=2");
        assertEquals(4, test.evaluate(Map.of()));
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void basicEvalWithNewVariable() {
        // Note that addvar and clearvars aren"t actually things anymore, so this tests the resilience of the UI to bad commands.
        KeywordInterface.safeKeywords("clearvars");
        KeywordInterface.safeKeywords("addvar y");
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("eval y^2 y=2");
        assertEquals(4, test.evaluate(Map.of()));
        KeywordInterface.safeKeywords("clearvars");
        KeywordInterface.safeKeywords("addvars x y z");
    }

    @Test
    void basicEvalWithThreeVariables() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("eval y+x+z x=3 z=1 y=2");
        assertEquals(6, test.evaluate(Map.of()));
    }

    @Test
    void basicSimplify() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("simp 1*(x+2x+0)");
        GeneralFunction test2 = FunctionParser.parseInfix("3x");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void notSoBasicSubstitute() {
        KeywordInterface.safeKeywords("def t x^2");
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("sa sub z+y y=t");
        GeneralFunction test2 = FunctionParser.parseInfix("z+x^2");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void basicSolve() {
        double[] test = ((List<Double>) KeywordInterface.safeKeywords("solve 5-5x 0 2")).stream().mapToDouble(i -> i).toArray();
        assertArrayEquals(new double[]{1}, test, Settings.equalsMargin);
    }

    @Test
    void basicExtremaMax() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("extrema max 1-x^2 -\\pi \\pi");
        assertEquals(0, test.evaluate(Map.of()));
    }

    @Test
    void basicTaylor() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.safeKeywords("tay \\cos(x) 1 0");
        GeneralFunction test2 = FunctionParser.parseInfix("1");
        assertEquals(test2.simplify(settings), test1);
    }

    @Test
    void basicNumericalIntegration() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("intn \\sin(x) 0 \\pi");
        assertEquals(2, test.evaluate(Map.of()), 0.01);
    }

    @Test
    void basicNumericalIntegrationWithError() {
        double[] test = (double[]) KeywordInterface.safeKeywords("intne \\sin(x) 0 \\pi");
        assertArrayEquals(new double[]{2, 0.01}, test, 0.01);
    }

    @Test
    void basicApostropheParsing() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("def \\f' 2x");
        assertEquals(new Product(new Constant(2), new Variable("x")), test);
    }

    @Test
    void basicAlphaNumerics() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("\\ts543s");
        assertEquals(new Variable("\\ts543s"), test);
    }

    @Test
    void basicUnderscoreParsing() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("\\f2_4_5");
        assertEquals(new Variable("\\f2_4_5"), test);
    }

    @Test
    void basicVariableParsing() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("\\test");
        assertEquals(new Variable("\\test"), test);
    }

    @Test
    void integralEscapedVariable() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("simp \\int(2\\test)\\d\\test");
        assertEquals(DefaultFunctions.square(new Variable("\\test")), test);
    }

    @Test
    void basicTrigExample() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("def \\trig (\\sin(\\number1 * \\theta))^2 + (\\cos(\\number2 * \\theta))^2 ");
        assertEquals(new Sum(new Pow(DefaultFunctions.TWO, new Sin(new Product(new Variable("\\number1"), new Variable("θ")))), new Pow(DefaultFunctions.TWO, new Cos(new Product(new Variable("\\number2"), new Variable("θ"))))), test);
    }

    @Test
    void gammaThing1() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("d/d\\Gamma[\\Gamma]");
        assertEquals(new Constant(1), test);
    }

    @Test
    void gammaThing2() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.safeKeywords("eval \\Gamma \\Gamma=1");
        assertEquals(1, test.evaluate(Map.of()));
    }

}

