package tools.integral;

import functions.Function;
import functions.commutative.Product;
import functions.commutative.Sum;
import functions.special.Constant;
import tools.FunctionTools;
import tools.helperclasses.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class IntegralsTools {
    public static Pair<Double, Function> stripConstants(Function function) {
        if (!(function instanceof Product))
            return new Pair<Double, Function>(1.0, function);
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
                return new Pair<Double, Function>(constant, termsWithConstantRemoved[0]);
            else
                return new Pair<Double, Function>(constant, (new Product(termsWithConstantRemoved)).simplifyPull());
        } else {
            throw new RuntimeException("This should never happen");
        }
    }
}
