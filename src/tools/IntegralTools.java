package tools;

import config.Settings;
import functions.GeneralFunction;
import functions.commutative.Product;
import tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class IntegralTools {

    private IntegralTools(){}

    /**
     * If {@code function} is not a {@link Product}, returns {@code <1.0, function>}. Otherwise, strips {@code function} of any functions that are constant relative to {@code varID} and returns a {@link Pair} of the constant function and the remaining stripped function. Ex: {@code 2xy, 'x'} becomes {@code <2y, x>}
     * @param function The {@link GeneralFunction} whose relative constants are being stripped
     * @param varID the variable to strip with respect to (i.e. {@code varID='x'} would mean {@code 2y} is a constant)
     * @return A {@link Pair} of the constant function and the remaining stripped function
     */
    public static Pair<GeneralFunction, GeneralFunction> stripConstantsRespectTo(GeneralFunction function, char varID) {
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

    /**
     * Simplifies the input without executing any of the optional simplifications steps enabled and disabled in {@link Settings}
     * @param function the function to be minimally simplified
     * @return the minimally simplified version of the function
     */
    public static GeneralFunction minimalSimplify(GeneralFunction function) {
        boolean dF = Settings.distributeFunctions;
        Settings.distributeFunctions = false;
        GeneralFunction simplified = function.simplify();
        Settings.distributeFunctions = dF;
        return simplified;
    }
}
