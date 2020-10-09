import org.junit.jupiter.api.Test;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgebraTests {

    @Test
    void lnOfAProduct() {
        Ln test = new Ln(new Product(new Variable("x"), new Variable("y")));
        assertEquals(new Sum(new Ln(new Variable("x")), new Ln(new Variable("y"))), LogSimplify.logarithmOfAProduct(test));
    }

    @Test
    void lnOfAnExponent() {
        Ln test = new Ln(new Pow(new Variable("x"), new Variable("y")));
        assertEquals(new Product(new Variable("x"), new Ln(new Variable("y"))), LogSimplify.logarithmOfAnExponent(test));
    }

    @Test
    void changeOfBase() {
        Logb test = new Logb(new Variable("x"), new Constant(10));
        assertEquals(DefaultFunctions.frac(new Ln(new Variable("x")), new Ln(new Constant(10))).simplify(), LogSimplify.changeOfBase(test));
    }

    @Test
    void logChainRuleBasic() {
        Variable X = new Variable("x");
        Variable Y = new Variable("y");
        Variable Z = new Variable("z");

        Product test = new Product(new Logb(Y, X), new Logb(Z, Y));
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(test));
    }

    @Test
    void logChainRuleBassdfasdfsdaic() {
        Variable X = new Variable("x");
        Variable Y = new Variable("y");
        Variable Z = new Variable("z");
        Variable A = new Variable("a");


        Product test = new Product(new Pow(Y, A), new Pow(X, A), new Pow(Z, A));
        System.out.println(test.addExponents());
        assertEquals(new Logb(Z, X), LogSimplify.logChainRule(test));
    }

}
