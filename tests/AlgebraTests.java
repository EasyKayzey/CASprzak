import org.junit.jupiter.api.Test;
import core.functions.GeneralFunction;
import core.functions.binary.Logb;
import core.functions.binary.Pow;
import core.functions.commutative.Product;
import core.functions.commutative.Sum;
import core.functions.endpoint.Constant;
import core.functions.endpoint.Variable;
import core.functions.unitary.specialcases.Exp;
import core.functions.unitary.specialcases.Ln;
import core.tools.algebra.LogSimplify;
import core.tools.defaults.DefaultFunctions;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static core.tools.defaults.DefaultFunctions.*;

public class AlgebraTests {

    private static final Variable A = new Variable("a");
    private static final Variable B = new Variable("b");
    private static final Variable C = new Variable("c");


    @Test
    void lnOfAProduct() {
        Ln test = new Ln(new Product(X, Y));
        assertEquals(new Sum(new Ln(X), new Ln(Y)), LogSimplify.logarithmOfAProduct(test));
    }

    @Test
    void lnOfAnExponent() {
        Ln test = new Ln(new Pow(X, Y));
        assertEquals(new Product(X, new Ln(Y)), LogSimplify.logarithmOfAnExponent(test));
    }

    @Test
    void changeOfBase() {
        Logb test = new Logb(X, new Constant(10));
        assertEquals(DefaultFunctions.frac(new Ln(X), new Ln(new Constant(10))).simplify(), LogSimplify.changeOfBase(test));
    }

    @Test
    void logChainRuleBasic() {
        Product test = new Product(new Logb(Y, X), new Logb(Z, Y));
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(test));
    }

    @Test
    void logChainRuleMany() {
        GeneralFunction test = LogSimplify.logChainRule(new Product(new Logb(Y, X), new Logb(T, PI), new Logb(Z, Y), new Ln(Y), new Logb(PI, T)));
        if (test.equals(new Product(new Logb(Z, X), new Ln(Y))))
            assertEquals(new Product(new Logb(Z, X), new Ln(Y)), test);
        else
            assertEquals(new Product(new Logb(Y, X), new Ln(Z)), test);
    }

    @Test
    void logChainRuleCycle() {
        Product test = new Product(new Logb(Y, X), new Logb(Z, Y), new Logb(X, Z));
        assertEquals(ONE, LogSimplify.logChainRule(test));
    }

    @Test
    void logChainRuleCOB() {
        Product test = new Product(new Logb(Y, X), new Pow(NEGATIVE_ONE, new Logb(Y, Z)));
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(test));
    }


    @Test
    void sumOfLogsBasic() {
        Sum test = new Sum(new Ln(X), new Ln(Y));
        assertEquals(new Ln(new Product(X,Y)), LogSimplify.sumOfLogs(test));
    }

    @Test
    void sumOfLogsComplex() {
        Sum test = new Sum(new Logb(Z, A), new Logb(X, A), new Logb(Y, B), new Logb(Z, C), new Logb(Z, B), new Logb(Y, C));
        assertEquals(new Sum(new Logb(new Product(X, Z), A), new Logb(new Product(Y, Z), B), new Logb(new Product(Y, Z), C)), LogSimplify.sumOfLogs(test));
    }


}
