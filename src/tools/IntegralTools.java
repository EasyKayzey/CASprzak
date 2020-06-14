package tools;

import functions.GeneralFunction;
import functions.commutative.Product;
import tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * The {@link IntegralTools} class contains miscellaneous methods used in {@link tools.integration}.
 */
public class IntegralTools {

    private IntegralTools(){}

    /**
     * If {@code function} is not a {@link Product}, returns {@code <1.0, function>}. Otherwise, strips {@code function} of any functions that are constant relative to {@code varID} and returns a {@link Pair} of the constant function and the remaining stripped function. Ex: {@code 2xy, 'x'} becomes {@code <2y, x>}
     * @param function The {@link GeneralFunction} whose relative constants are being stripped
     * @param varID the variable to strip with respect to (i.e. {@code varID='x'} would mean {@code 2y} is a constant)
     * @return A {@link Pair} of the constant function and the remaining stripped function
     */
    public static Pair<GeneralFunction, GeneralFunction> stripConstantsRespectTo(GeneralFunction function, String varID) {
        if (function instanceof Product multiply) {
            GeneralFunction[] terms = multiply.simplifyConstants().getFunctions();
            List<GeneralFunction> constants = new ArrayList<>();
            List<GeneralFunction> termsWithConstantRemoved = new ArrayList<>(Arrays.asList(terms));
            ListIterator<GeneralFunction> iter = termsWithConstantRemoved.listIterator();
            while (iter.hasNext()) {
                GeneralFunction current = iter.next();
                if (VariableTools.doesNotContainsVariable(current, varID)) {
                    constants.add(current);
                    iter.remove();
                }
            }
            return new Pair<>(new Product(constants.toArray(new GeneralFunction[0])).simplifyTrivialElement(), new Product(termsWithConstantRemoved.toArray(new GeneralFunction[0])).simplifyTrivialElement());
        } else {
            if (VariableTools.doesNotContainsVariable(function, varID))
                return new Pair<>(function, DefaultFunctions.ONE);
            else
                return new Pair<>(DefaultFunctions.ONE, function);
        }
    }

}
