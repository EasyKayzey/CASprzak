import org.junit.jupiter.api.Test;
import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.endpoint.Variable;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;
import show.ezkz.casprzak.core.tools.algebra.LogSimplify;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgebraTests {

    @Test
    void lnOfAProduct() {
        Ln test = new Ln(new Product(new Variable("x"), new Variable("y")));
        assertEquals(new Sum(new Ln(new Variable("x")), new Ln(new Variable("y"))), LogSimplify.logarithmOfAProduct(test));
    }

}
