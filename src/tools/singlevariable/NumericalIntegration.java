package tools.singlevariable;

import config.Settings;
import functions.GeneralFunction;
import tools.VariableTools;

import java.util.Map;

public class NumericalIntegration {
    
    /**
     * Returns the approximate definite integral of a {@link GeneralFunction} function on a range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the approximate definite integral of the function on a range
     */
    public static double simpsonsRule(GeneralFunction function, double lowerBound, double upperBound) {
        if (Settings.simpsonsSegments % 2 != 0)
            throw new IllegalArgumentException("Amount of segments for Simpson's rule must be even.");
        char var = VariableTools.getSingleVariable(function);
        double step = (upperBound - lowerBound) / Settings.simpsonsSegments;
        double x = lowerBound + step;
        double sum = function.evaluate(Map.of(var, lowerBound));
        for (int i = 1; i < Settings.simpsonsSegments / 2; i++) {
            sum += 4 * function.evaluate(Map.of(var, x));
            x += step;
            sum += 2 * function.evaluate(Map.of(var, x));
            x += step;
        }
        sum += 4*function.evaluate(Map.of(var, x));
        x += step;
        sum += function.evaluate(Map.of(var, x));
        sum *= step / 3;
        return sum;
    }

    /**
     * Returns the maximum error associated with the definite integral of a {@link GeneralFunction} function on a range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the maximum error associated with the definite integral of the function on a range
     */
    public static double simpsonsError(GeneralFunction function, double lowerBound, double upperBound) {
        char var = VariableTools.getSingleVariable(function);
        GeneralFunction fourthDerivative = function.getNthDerivative(var, 4);
        return fourthDerivative.evaluate(Map.of(var, Extrema.findMaximumOnRange(fourthDerivative, lowerBound, upperBound))) * Math.pow(upperBound - lowerBound, 5) / (180 * Math.pow(Settings.simpsonsSegments, 4));
    }

    /**
     * Returns the approximate definite integral of a {@link GeneralFunction} function on a range with an error range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return a double array comprised of the approximate definite integral of the function on a range and its error range
     */
    public static double[] simpsonsRuleWithError(GeneralFunction function, double lowerBound, double upperBound) {
        return new double[]{simpsonsRule(function, lowerBound, upperBound), simpsonsError(function, lowerBound, upperBound)};
    }

}
