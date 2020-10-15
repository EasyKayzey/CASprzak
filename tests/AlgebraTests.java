import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.config.SimplificationSettings;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Exp;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;
import show.ezkz.casprzak.core.tools.algebra.LogSimplify;
import show.ezkz.casprzak.core.tools.defaults.DefaultFunctions;
import show.ezkz.casprzak.core.tools.defaults.DefaultSimplificationSettings;

import java.awt.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static show.ezkz.casprzak.core.tools.defaults.DefaultFunctions.*;

public class AlgebraTests {

    private static final Variable A = new Variable("a");
    private static final Variable B = new Variable("b");
    private static final Variable C = new Variable("c");

    private static final SimplificationSettings settings = DefaultSimplificationSettings.minimal;

    @Test
    void lnOfAProduct() {
        Ln test = new Ln(new Product(X, Y));
        assertEquals(new Sum(new Ln(X), new Ln(Y)), LogSimplify.logarithmOfAProduct(settings, test));
    }

    @Test
    void lnOfAnExponent() {
        Ln test = new Ln(new Pow(X, Y));
        assertEquals(new Product(X, new Ln(Y)), LogSimplify.logarithmOfAnExponent(settings, test));
    }

    @Test
    void changeOfBase() {
        Logb test = new Logb(X, new Constant(10));
        assertEquals(DefaultFunctions.frac(new Ln(X), new Ln(new Constant(10))).simplify(settings), LogSimplify.changeOfBase(settings, test));
    }

    @Test
    void logChainRuleBasic() {
        Product test = new Product(new Logb(Y, X), new Logb(Z, Y));
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(settings, test));
    }

    @Test
    void logChainRuleMany() {
        GeneralFunction test = LogSimplify.logChainRule(settings, new Product(new Logb(Y, X), new Logb(T, PI), new Logb(Z, Y), new Ln(Y), new Logb(PI, T)));
        if (test.equals(new Product(new Logb(Z, X), new Ln(Y))))
            assertEquals(new Product(new Logb(Z, X), new Ln(Y)), test);
        else
            assertEquals(new Product(new Logb(Y, X), new Ln(Z)), test);
    }

    @Test
    void logChainRuleCycle() {
        Product test = new Product(new Logb(Y, X), new Logb(Z, Y), new Logb(X, Z));
        assertEquals(ONE, LogSimplify.logChainRule(settings, test));
    }

    @Test
    void logChainRuleCOB() {
        Product test = new Product(new Logb(Y, X), new Pow(NEGATIVE_ONE, new Logb(Y, Z)));
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(settings, test));
    }


    @Test
    void sumOfLogsBasic() {
        Sum test = new Sum(new Ln(X), new Ln(Y));
        assertEquals(new Ln(new Product(X,Y)), LogSimplify.sumOfLogs(settings, test));
    }

    @Test
    void sumOfLogsComplex() {
        Sum test = new Sum(new Logb(Z, A), new Logb(X, A), new Logb(Y, B), new Logb(Z, C), new Logb(Z, B), new Logb(Y, C));
        assertEquals(new Sum(new Logb(new Product(X, Z), A), new Logb(new Product(Y, Z), B), new Logb(new Product(Y, Z), C)), LogSimplify.sumOfLogs(settings, test));
    }


}
