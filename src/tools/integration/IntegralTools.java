package tools.integration;

import config.Settings;
import functions.GeneralFunction;
import functions.commutative.Product;
import tools.DefaultFunctions;
import tools.VariableTools;
import tools.helperclasses.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class IntegralTools {

    /**
     * Strips a {@link GeneralFunction} of any relative constants and returns a {@link Pair} of the constant function and the stripped GeneralFunction (note that 2y is treated as a constant with respect to x)
     * @param function The GeneralFunction whose Constant is being Stripped
     * @param varID the variable to strip with respect to (i.e. varID='x' would mean '2y' is a constant)
     * @return A {@link Pair} of the constant and the stripped GeneralFunction
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
            return new Pair<>(DefaultFunctions.ONE, function);
        }
    }

    public static GeneralFunction minimalSimplify(GeneralFunction function) {
        boolean dF = Settings.distributeFunctions;
        Settings.distributeFunctions = false;
        GeneralFunction simplified = function.simplify();
        Settings.distributeFunctions = dF;
        return simplified;
    }
}
