import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.binary.Pow;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Constant;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
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

}
