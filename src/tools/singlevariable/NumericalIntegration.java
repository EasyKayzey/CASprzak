package tools.singlevariable;

import core.Settings;
import functions.Function;

public class NumericalIntegration {


    /**
     * Returns the approximate definite integral of a {@link Function} function on a range
     * @param function The {@link Function} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the approximate definite integral of function on a range
     */
    public static double simpsonsRule(Function function, double lowerBound, double upperBound) {
        double sum = function.evaluate(lowerBound);
        double step = (upperBound-lowerBound)/ Settings.amountOfSegments;
        double x = lowerBound + step;
        for (int i = 1; i < Settings.amountOfSegments/2; i++) {
            sum += 4*function.evaluate(x);
            x += step;
            sum += 2*function.evaluate(x);
            x += step;
        }
        sum += 4*function.evaluate(x);
        x += step;
        sum += function.evaluate(x);
        sum *= step/3;
        return sum;
    }
}
