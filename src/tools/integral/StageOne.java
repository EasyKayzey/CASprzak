package tools.integral;

import functions.Function;
import functions.binary.Logb;
import functions.binary.Pow;
import functions.special.Constant;
import tools.SearchTools;

public class StageOne {
    public static Function derivativeDivides(Function function, char variableChar) {
        Class<?> operator;
        Function u;
        double number = 1;

        for (Function f: function) {
            if (f instanceof Pow power && power.getFunction1() instanceof Constant constant) {
                if (constant.constant != -1) {

                } else {

                }
            }
        }


        return function;
        // Let's say I want to check if all xs are in the form e^x
//        SearchTools.exists(integrand, (f -> f.equals(toFind))) && !SearchTools.existsExcluding(integrand, (f -> f instanceof Variable), (f -> f.equals(toFind)))
    }


}
