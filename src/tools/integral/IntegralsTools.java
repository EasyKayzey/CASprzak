package tools.integral;

import functions.Function;
import functions.commutative.Multiply;
import functions.special.Constant;
import tools.FunctionTools;
import tools.helperclasses.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class IntegralsTools {
    public static Pair<Double, Function> stripConstants(Function function) {
        if (!(function instanceof Multiply))
            return new Pair(1.0, function);
        else if (function instanceof Multiply multiply) {
            Function[] terms = multiply.simplifyConstants().getFunctions();
            double constant = 1;
            Function[] termsWithConstantRemoved = terms;
            for (int i = 0; i < multiply.getFunctions().length; i++) {
                if (terms[i] instanceof Constant number) {
                    constant *= number.constant;
                    termsWithConstantRemoved = FunctionTools.removeFunctionAt(terms, i);
                }
            }
            return new Pair(constant, (new Multiply(termsWithConstantRemoved)).simplifyPull());
        } else {
            throw new RuntimeException("This should never happen");
        }
    }
}
