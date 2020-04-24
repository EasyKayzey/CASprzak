package tools.singlevariable;

import functions.Function;

public class NumericalIntegration {
    public static final double amountOfSegments = 500;


    public static double simpsonsRule(Function function, double lowerBound, double upperBound) {
        double sum = function.evaluate(lowerBound);
        double step = 0.5*(upperBound-lowerBound);
        double x = lowerBound + step;
        for (int i = 1; i < amountOfSegments/2; i++) {
            sum += 4*function.evaluate(x);
            x += step;
            sum += 2*function.evaluate(x);
            x += step;
        }
        sum += function.evaluate(sum);
        return sum;
    }
}
