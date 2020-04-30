package tools.integral;

import functions.Function;
import functions.commutative.Product;
import functions.special.Constant;
import tools.FunctionTools;
import tools.helperclasses.Pair;


public class IntegralsTools {
    public static Pair<Double, Function> stripConstants(Function function) {
        if (!(function instanceof Product))
            return new Pair<>(1.0, function);
        else if (function instanceof Product multiply) {
            Function[] terms = multiply.simplifyConstants().getFunctions();
            double constant = 1;
            Function[] termsWithConstantRemoved = terms;
            for (int i = 0; i < multiply.getFunctions().length; i++) {
                if (terms[i] instanceof Constant number) {
                    constant *= number.constant;
                    termsWithConstantRemoved = FunctionTools.removeFunctionAt(terms, i);
                }
            }
            if (termsWithConstantRemoved.length == 1)
                return new Pair<>(constant, termsWithConstantRemoved[0]);
            else
                return new Pair<>(constant, (new Product(termsWithConstantRemoved)).simplifyPull());
        } else {
            throw new RuntimeException("This should never happen");
        }
    }
}
