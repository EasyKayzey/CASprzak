package tools.integral;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.special.Constant;
import tools.SearchTools;
import tools.helperclasses.Pair;

public class StageOne {
    public static Function derivativeDivides(Function integrand, char variableChar) {
        Class<?> operator;
        Pair<Double, Function> stripConstant = IntegralsTools.stripConstants(integrand);
        Function function = stripConstant.second;
        double number = stripConstant.first;
        Function u;



        return function;
        // Let's say I want to check if all xs are in the form e^x
//        SearchTools.exists(integrand, (f -> f.equals(toFind))) && !SearchTools.existsExcluding(integrand, (f -> f instanceof Variable), (f -> f.equals(toFind)))
    }


}
