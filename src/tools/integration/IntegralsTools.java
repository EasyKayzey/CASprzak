package tools.integration;

import functions.Function;
import functions.commutative.Product;
import functions.special.Variable;
import tools.DefaultFunctions;
import tools.FunctionTools;
import tools.SearchTools;
import tools.helperclasses.Pair;


public class IntegralsTools {

    /**
     * Strips a {@link Function} of any Constants and returns a {@link Pair} of the constant and the stripped Function
     * @param function The Function whose Constant is being Stripped
     * @return A {@link Pair} of the constant and the stripped Function
     */
    public static Pair<Function, Function> stripConstants(Function function, char varID) {
        if (function instanceof Product multiply) {
            Function[] terms = multiply.simplifyConstants().getFunctions();
            Product constants = new Product();
            Function[] termsWithConstantRemoved = terms;
            for (int i = 0; i < multiply.getFunctions().length; i++) {
                if (doesNotContainsVariable(terms[i], varID)) {
                    constants = new Product(constants, terms[i]);
                    termsWithConstantRemoved = FunctionTools.removeFunctionAt(terms, i);
                }
            }
            if (termsWithConstantRemoved.length == 1)
                return new Pair<>(constants, termsWithConstantRemoved[0]);
            else
                return new Pair<>(constants, (new Product(termsWithConstantRemoved)).simplifyPull());
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
