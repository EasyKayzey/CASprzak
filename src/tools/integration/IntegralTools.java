package tools.integration;

import functions.Function;
import functions.commutative.Product;
import functions.special.Variable;
import tools.DefaultFunctions;
import tools.SearchTools;
import tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class IntegralTools {

    /**
     * Strips a {@link Function} of any Constants and returns a {@link Pair} of the constant and the stripped Function
     * @param function The Function whose Constant is being Stripped
     * @return A {@link Pair} of the constant and the stripped Function
     */
    public static Pair<Function, Function> stripConstants(Function function, char varID) {
        if (function instanceof Product multiply) {
            Function[] terms = multiply.simplifyConstants().getFunctions();
            List<Function> constants = new ArrayList<>();
            List<Function> termsWithConstantRemoved = new ArrayList<>(Arrays.asList(terms));
            ListIterator<Function> iter = termsWithConstantRemoved.listIterator();
            while (iter.hasNext()) {
                Function current = iter.next();
                if (doesNotContainsVariable(current, varID)) {
                    constants.add(current);
                    iter.remove();
                }
            }
            return new Pair<>((new Product(constants.toArray(new Function[0]))).simplify(), (new Product(termsWithConstantRemoved.toArray(new Function[0]))).simplifyPull().simplifyTrivialElement());
        } else {
            return new Pair<>(DefaultFunctions.ONE, function);
        }
    }

    /**
     * Returns true if the {@link Variable} is found in the {@link Function}
     * @param function The Function that is being searched
     * @param varID The variable ID of the variable that is being looked for
     * @return true if the {@link Variable} is found in the {@link Function}
     */
    public static boolean doesNotContainsVariable(Function function, char varID) {
        Variable variable = new Variable(varID);
        return !SearchTools.exists(function, variable::equals);
    }
}
