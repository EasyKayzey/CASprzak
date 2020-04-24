package tools.singlevariable;

import functions.Function;

public class NumericalIntegration {
    /**
     * The amount of segments the region can be broken into (MUST BE EVEN)
     */
    public static final double amountOfSegments = 500;


    /**
     * Returns the approximate definite integral of a {@link Function} function on a range
     * @param function The {@link Function} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the approximate definite integral of function on a range
     */
    public static double simpsonsRule(Function function, double lowerBound, double upperBound) {
        double sum = function.evaluate(lowerBound);
        double step = (upperBound-lowerBound)/amountOfSegments;
        double x = lowerBound + step;
        for (int i = 1; i < amountOfSegments/2; i++) {
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
