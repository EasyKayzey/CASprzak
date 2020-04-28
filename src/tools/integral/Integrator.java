package tools.integral;

import functions.Function;
import functions.commutative.Add;
import functions.special.Variable;

public class Integrator {
    public static boolean hasBeenFullyIntegrated;

    public static Function integral(Function function, char variableChar) {
        hasBeenFullyIntegrated = false;
        Function functionAfterStageOne = StageOne.derivativeDivides(function, variableChar);
        if (hasBeenFullyIntegrated) return new Add(functionAfterStageOne, new Variable('C'));


        return function;
    }
}
