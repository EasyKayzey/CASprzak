package show.ezkz.casprzak.core.tools.algebra;

import show.ezkz.casprzak.core.functions.GeneralFunction;
import show.ezkz.casprzak.core.functions.binary.Logb;
import show.ezkz.casprzak.core.functions.commutative.Product;
import show.ezkz.casprzak.core.functions.commutative.Sum;
import show.ezkz.casprzak.core.functions.unitary.specialcases.Ln;

import java.util.Arrays;

public class LogSimplify {

    public static GeneralFunction expand(GeneralFunction input) {
        if (!(input instanceof Logb) && !(input instanceof Ln))
            throw new IllegalArgumentException("expand should not be called on a non logarithm.");


        return null;
    }

    public static GeneralFunction simplify(GeneralFunction input) {
        return null;
    }

    public static GeneralFunction logarithmOfAProduct(Ln input) {
        GeneralFunction operand = input.operand;

        if (operand instanceof Product product) {
            GeneralFunction[] terms = (GeneralFunction[]) Arrays.stream(product.getFunctions()).map(Ln::new).toArray();
            return new Sum(terms);
        } else
            return input;
    }





}
