package tools.singlevariable;

import config.Settings;
import functions.GeneralFunction;

import java.util.Map;

public class NumericalIntegration {
    
    /**
     * Returns the approximate definite integral of a {@link GeneralFunction} function on a range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the approximate definite integral of function on a range
     */
    public static double simpsonsRule(GeneralFunction function, double lowerBound, double upperBound) {
        double sum = function.evaluate(Map.of(Settings.singleVariableDefault, lowerBound));
        double step = (upperBound-lowerBound)/ Settings.simpsonsSegments;
        double x = lowerBound + step;
        for (int i = 1; i < Settings.simpsonsSegments /2; i++) {
            sum += 4*function.evaluate(Map.of(Settings.singleVariableDefault, x));
            x += step;
            sum += 2*function.evaluate(Map.of(Settings.singleVariableDefault, x));
            x += step;
        }
        sum += 4*function.evaluate(Map.of(Settings.singleVariableDefault, x));
        x += step;
        sum += function.evaluate(Map.of(Settings.singleVariableDefault, x));
        sum *= step/3;
        return sum;
    }

    /**
     * Returns the maximum error associated with the definite integral of a {@link GeneralFunction} function on a range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the maximum error associated with the definite integral of function on a range
     */
    public static double simpsonsError(GeneralFunction function, double lowerBound, double upperBound) {
        GeneralFunction fourthDerivative = function.getNthDerivative(Settings.singleVariableDefault, 4);
        return fourthDerivative.evaluate(Map.of(Settings.singleVariableDefault, Extrema.findMaximumOnRange(fourthDerivative, lowerBound, upperBound)))*Math.pow(upperBound - lowerBound, 5) / (180 * Math.pow(Settings.simpsonsSegments, 4));
    }

    /**
     * Returns the approximate definite integral of a {@link GeneralFunction} function on a range with an error range
     * @param function The {@link GeneralFunction} whose integral is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the approximate definite integral of function on a range with an error range
     */
    public static double[] simpsonsRuleWithError(GeneralFunction function, double lowerBound, double upperBound) {
        return new double[]{simpsonsRule(function, lowerBound, upperBound), simpsonsError(function, lowerBound, upperBound)};
    }

}
