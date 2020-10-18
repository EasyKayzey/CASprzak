import show.ezkz.casprzak.core.config.Settings;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;
import show.ezkz.casprzak.parsing.FunctionParser;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings.aggressive;

public class SimplifyTest {

    private static final SimplificationSettings settings = DefaultSimplificationSettings.aggressive;
    private static final SimplificationSettings settings1 = new SimplificationSettings(
            settings.simplifyFunctionsOfConstants,
            settings.simplifyFunctionsOfSpecialConstants,
            settings.distributeExponentsOverMultiplication,
            settings.addExponentsInProducts,
            settings.unwrapIntegerPowers,
            settings.distributeMultiplicationOverAddition,
            settings.multiplyExponentsOfExponents,
            settings.executeTransformsOnSimplify,
            false,
            settings.simplifyLogAddition,
            settings.expandLogOfProducts,
            settings.extractLogExponents,
            settings.doChangeOfBase,
            settings.doTrigIdentities,
            settings.trigComposition,
            settings.simplifyInverses
    );

    @Test
    void equalWhenSimplified() {
        GeneralFunction test1 = FunctionParser.parseInfix("x+(1+3-2)*1");
        GeneralFunction test2 = FunctionParser.parseInfix("x+2");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void constantEquality() {
        GeneralFunction test1, test2;
        test1 = FunctionParser.parseInfix("1");
        test2 = FunctionParser.parseInfix("0+1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
        test1 = FunctionParser.parseInfix("1");
        test2 = FunctionParser.parseInfix("2");
        assertNotEquals(test1, test2);
        test1 = FunctionParser.parseInfix("e");
        test2 = FunctionParser.parseInfix("" + Math.E);
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifiesDivisionExponents() {
        GeneralFunction test1 = FunctionParser.parseInfix("x^3/x^2");
        GeneralFunction test2 = FunctionParser.parseInfix("x");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void combineLikeTerms() {
        GeneralFunction test1 = FunctionParser.parseInfix("3*x^2+5*x^-1+7*x^-1-3*x^2+1");
        GeneralFunction test2 = FunctionParser.parseInfix("1+12*x^-1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void distributeTerms() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\sin(x)*(1+5x)");
        GeneralFunction test2 = FunctionParser.parseInfix("\\sin(x)+5*x*\\sin(x)");
        assertEquals(((Product)test1).distributeAll(settings), test2);
    }

    @Test
    void distributeExponents() {
        GeneralFunction test1 = FunctionParser.parseInfix("(2xy)^2");
        GeneralFunction test2 = FunctionParser.parseInfix("4x^2y^2");
        assertEquals(test1.simplify(aggressive), test2.simplify(aggressive));
    }

    @Test
    void addExponents() {
        GeneralFunction test1 = FunctionParser.parseInfix("x^2*x^4");
        GeneralFunction test2 = FunctionParser.parseInfix("x^6");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void combineLikeTermsMultExample() {
        GeneralFunction test1 = FunctionParser.parseInfix("(3+x)(2+x)(1+x)");
        GeneralFunction test2 = FunctionParser.parseInfix("x^3+11x+6x^2+6");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifySimpleExponents() {
        GeneralFunction test1 = FunctionParser.parseInfix("(x+1)^1");
        GeneralFunction test2 = FunctionParser.parseInfix("x+1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifyMultiplyExponents() {
        GeneralFunction test1 = FunctionParser.parseInfix("(x^3)^2");
        GeneralFunction test2 = FunctionParser.parseInfix("x^6");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifyIdentity() {
        GeneralFunction test1 = FunctionParser.parseInfix("x+0");
        GeneralFunction test2 = FunctionParser.parseInfix("x*1");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifyPullAdd() {
        GeneralFunction test1 = FunctionParser.parseInfix("(x+(y+z))");
        GeneralFunction test2 = FunctionParser.parseInfix("x+y+z");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simplifyPullMultiply() {
        GeneralFunction test1 = FunctionParser.parseInfix("x*(yz)");
        GeneralFunction test2 = FunctionParser.parseInfix("x*y*z");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void timesZero() {
        GeneralFunction test1 = FunctionParser.parseInfix("x*0");
        GeneralFunction test2 = FunctionParser.parseInfix("0");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void unwrapPowersTest() {
        GeneralFunction test1 = FunctionParser.parseInfix("(x+1)^3");
        GeneralFunction test2 = FunctionParser.parseInfix("(x+1)*(x+1)*(x+1)");
        assertEquals(((Pow) test1).unwrapIntegerPowerSafe(settings).simplify(settings), test2.simplify(settings));
    }

    @Test
    void unwrapPowersEdgeTest() {
        GeneralFunction test1 = FunctionParser.parseInfix("(x+1)^0");
        GeneralFunction test2 = FunctionParser.parseInfix("1");
        assertEquals(((Pow) test1).unwrapIntegerPowerSafe(settings).simplify(settings), test2);
    }

    @Test
    void simpleInverseLnAndExp() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\exp(\\ln(x))");
        GeneralFunction test2 = FunctionParser.parseInfix("\\ln(\\exp(x))");
        assertEquals(test1.simplify(settings), test2.simplify(settings));
    }

    @Test
    void simpleInverseTrig() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\asin(\\sin(x))");
        GeneralFunction test2 = FunctionParser.parseInfix("\\cos(\\acos(x))");
        assertEquals(test1.simplify(settings1), test2.simplify(settings1));
    }

    @Test
    void inverseChain() {
        GeneralFunction test1 = FunctionParser.parseInfix("\\asin(\\acos(\\exp(\\ln(\\sec(\\asec(\\cos(\\sin(x))))))))");
        assertEquals(DefaultFunctions.X, test1.simplify(settings1)); // this isn't actually correct based on ranges, so if you add that this will break
    }

    @Test
    void expInverses() {
        GeneralFunction test1 = FunctionParser.parseSimplified("\\ln(e^\\logb_e(\\exp(x)))");
        assertEquals(DefaultFunctions.X, test1); // this isn't actually correct based on ranges, so if you add that this will break
    }

    @Test
    void logFOC() {
        GeneralFunction test1 = FunctionParser.parseSimplified("\\log(100)");
        assertEquals(DefaultFunctions.TWO, test1);
    }
}
