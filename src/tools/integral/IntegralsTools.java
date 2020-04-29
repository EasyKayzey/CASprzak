package tools.integral;

import functions.Function;
import functions.commutative.Multiply;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class IntegralsTools {
    public static Map.Entry<Double, Function> stripConstants(Function function) {
        if (!(function instanceof Multiply))
            return new AbstractMap.SimpleEntrty(1.0, function);
        else if (function instanceof Multiply multiply) {
            Function[] terms = multiply.getFunctions();
            double constant = 1;

        }
    }
}
