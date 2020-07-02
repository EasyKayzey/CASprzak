import config.Settings;
import functions.GeneralFunction;
import functions.binary.Pow;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import functions.special.Variable;
import functions.unitary.trig.normal.Cos;
import functions.unitary.trig.normal.Sin;
import org.junit.jupiter.api.Test;
import parsing.FunctionParser;
import parsing.KeywordInterface;
import tools.DefaultFunctions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings({"SpellCheckingInspection", "unchecked"})
public class KeywordInterfaceTest {

    @Test
    void partialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pd x x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("2x");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void doublePartialDerivatives() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pd x pd x x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("2");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void partialWithRespectToVariableDoesNotExist() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pd n x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("0");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void partialOfASimp() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pd x simp 2x+3x");
        GeneralFunction test2 = FunctionParser.parseInfix("5");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void partialDerivativeNTimes() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pdn x 4 \\sin(x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(x)");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void basicEval() {
        double test = (double) KeywordInterface.useKeywords("eval x^2 x=2");
        assertEquals(4, test);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void basicEvalWithNewVariable() { // Note that addvar and clearvars aren"t actually things anymore, so this tests the resilience of the UI to bad commands.
        KeywordInterface.useKeywords("clearvars");
        KeywordInterface.useKeywords("addvar y");
        double test = (double) KeywordInterface.useKeywords("eval y^2 y=2");
        assertEquals(4, test);
        KeywordInterface.useKeywords("clearvars");
        KeywordInterface.useKeywords("addvars x y z");
    }

    @Test
    void basicEvalWithThreeVariables() {
        double test = (double) KeywordInterface.useKeywords("eval y+x+z x=3 z=1 y=2");
        assertEquals(6, test);
    }

    @Test
    void basicSimplify() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("simp 1*(x+2x+0)");
        GeneralFunction test2 = FunctionParser.parseInfix("3x");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void notSoBasicSubstitute() {
        KeywordInterface.useKeywords("def t x^2");
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("sa sub z+y y=t");
        GeneralFunction test2 = FunctionParser.parseInfix("z+x^2");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void basicSolve() {
        double[] test = ((List<Double>) KeywordInterface.useKeywords("solve 5-5x 0 2")).stream().mapToDouble(i -> i).toArray();
        assertArrayEquals(new double[]{1}, test, Settings.equalsMargin);
    }

    @Test
    void basicExtremaMax() {
        double test = (double) KeywordInterface.useKeywords("extrema max 1-x^2 -\\pi \\pi");
        assertEquals(0, test);
    }

    @Test
    void basicTaylor() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("tay \\cos(x) 1 0");
        GeneralFunction test2 = FunctionParser.parseInfix("1");
        assertEquals(test2.simplify(), test1);
    }

    @Test
    void basicNumericalIntegration() {
        double test = (double) KeywordInterface.useKeywords("intn \\sin(x) 0 \\pi");
        assertEquals(2, test, 0.01);
    }

    @Test
    void basicNumericalIntegrationWithError() {
        double[] test = (double[]) KeywordInterface.useKeywords("intne \\sin(x) 0 \\pi");
        assertArrayEquals(new double[]{2, 0.01}, test, 0.01);
    }

    @Test
    void basicApostropheParsing() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.useKeywords("def \\f' 2x");
        assertEquals(new Product(new Constant(2), new Variable("x")), test);
    }

    @Test
    void basicAlphaNumerics() {
        boolean temp = Settings.removeEscapes;
        Settings.removeEscapes = true;
        GeneralFunction test = (GeneralFunction) KeywordInterface.useKeywords("\\ts543s");
        assertEquals(new Variable("ts543s"), test);
        Settings.removeEscapes = false;
        test = (GeneralFunction) KeywordInterface.useKeywords("\\ts543s");
        assertEquals(new Variable("\\ts543s"), test);
        Settings.removeEscapes = temp;
    }

    @Test
    void basicUnderscoreParsing() {
        boolean temp = Settings.removeEscapes;
        Settings.removeEscapes = true;
        GeneralFunction test = (GeneralFunction) KeywordInterface.useKeywords("\\f2_4_5");
        assertEquals(new Variable("f2_4_5"), test);
        Settings.removeEscapes = false;
        test = (GeneralFunction) KeywordInterface.useKeywords("\\f2_4_5");
        assertEquals(new Variable("\\f2_4_5"), test);
        Settings.removeEscapes = temp;
    }

    @Test
    void basicVariableParsing() {
        boolean temp = Settings.removeEscapes;
        Settings.removeEscapes = true;
        GeneralFunction test = (GeneralFunction) KeywordInterface.useKeywords("\\test");
        assertEquals(new Variable("test"), test);
        Settings.removeEscapes = false;
        test = (GeneralFunction) KeywordInterface.useKeywords("\\test");
        assertEquals(new Variable("\\test"), test);
        Settings.removeEscapes = temp;
    }

    @Test
    void basicTrigExample() {
        GeneralFunction test = (GeneralFunction) KeywordInterface.useKeywords("def \\trig (\\sin(\\number1 * \\theta))^2 + (\\cos(\\number2 * \\theta))^2 ");
        assertEquals(new Sum(new Pow(DefaultFunctions.TWO, new Sin(new Product(new Variable("number1"), new Variable("\\theta")))), new Pow(DefaultFunctions.TWO, new Cos(new Product(new Variable("number2"), new Variable("\\theta"))))), test);
    }
}

