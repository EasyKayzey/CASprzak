package tools.singlevariable;

import config.Settings;
import functions.GeneralFunction;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public class Extrema {

    private Extrema(){}

    /**
     * Returns the local minima of a {@link GeneralFunction} function on a specified range
     * @param function The {@link GeneralFunction} whose minima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the local minima of function on the specified range
     */
    public static double findLocalMinimum(GeneralFunction function, double lowerBound, double upperBound) {
       double[] secondDerivativeIsPositive = findPoints(function, lowerBound, upperBound, (a, b) -> (a > b));
        double minimum = findSmallestOrLargest(function, secondDerivativeIsPositive, (a, b) -> (a < b));
        if (minimum > upperBound || minimum < lowerBound)
            return Double.NaN;
        return minimum;
    }


    /**
     * Returns the local maxima of a {@link GeneralFunction} function on a specified range
     * @param function The {@link GeneralFunction} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the local maxima of function on the specified range
     */
    public static double findLocalMaximum(GeneralFunction function, double lowerBound, double upperBound) {
        double[] secondDerivativeIsNegative = findPoints(function, lowerBound, upperBound, (a, b) -> (a < b));
        double maximum = findSmallestOrLargest(function, secondDerivativeIsNegative, (a, b) -> (a > b));
        if (maximum > upperBound || maximum < lowerBound)
            return Double.NaN;
        return maximum;
    }

    /**
     * Returns the maximum of a {@link GeneralFunction} function on a specified range (endpoints included)
     * @param function The {@link GeneralFunction} whose maximum is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the maximum of function on the specified range
     */
    public static double findMaximumOnRange(GeneralFunction function, double lowerBound, double upperBound) {
        double maximum = findLocalMaximum(function, lowerBound, upperBound);
        return findExtremumOnRange(function, lowerBound, upperBound, (a, b) -> (a > b), maximum);
    }

    /**
     * Returns the minimum of a {@link GeneralFunction} function on a specified range (endpoints included)
     * @param function The {@link GeneralFunction} whose minimum is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the minimum of function on the specified range
     */
    public static double findMinimumOnRange(GeneralFunction function, double lowerBound, double upperBound) {
        double minimum = findLocalMinimum(function, lowerBound, upperBound);
        return findExtremumOnRange(function, lowerBound, upperBound, (a, b) -> (a < b), minimum);
    }

    private static double findExtremumOnRange(GeneralFunction function, double lowerBound, double upperBound, BiPredicate<? super Double, ? super Double> strategy, double extremum) {
        if (Double.isNaN(extremum))
            extremum = lowerBound;
        else if (strategy.test(function.evaluate(Map.of(Settings.singleVariableDefault, lowerBound)), function.evaluate(Map.of(Settings.singleVariableDefault, extremum))))
            extremum = lowerBound;

        if (strategy.test(function.evaluate(Map.of(Settings.singleVariableDefault, upperBound)), function.evaluate(Map.of(Settings.singleVariableDefault, extremum))))
            extremum = upperBound;
        return extremum;
    }

    /**
     * Returns any minima of a {@link GeneralFunction} function on a specified range
     * @param function The {@link GeneralFunction} whose minima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any minima of function on the specified range
     */
    public static double[] findAnyMinima(GeneralFunction function, double lowerBound, double upperBound) {
        return findPoints(function, lowerBound, upperBound, (a, b) -> (a > b));
    }

    /**
     * Returns any maxima of a {@link GeneralFunction} function on a specified range
     * @param function The {@link GeneralFunction} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any maxima of function on the specified range
     */
    public static double[] findAnyMaxima(GeneralFunction function, double lowerBound, double upperBound) {
       return findPoints(function, lowerBound, upperBound, (a, b) -> (a < b));
    }

    /**
     * Returns any inflection point of a {@link GeneralFunction} function on a specified range
     * @param function The {@link GeneralFunction} whose inflection points are being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any inflection point of function on the specified range
     */
    public static double[] findAnyInflectionPoints(GeneralFunction function, double lowerBound, double upperBound) {
        return findPoints(function, lowerBound, upperBound, (a, b) -> (a - b < Settings.zeroMargin && b - a < Settings.zeroMargin));
    }

    private static double[] findPoints(GeneralFunction function, double lowerBound, double upperBound, BiPredicate<? super Double, ? super Double> strategy) {
        double[] criticalPoints = Solver.getSolutionsRange(function.getSimplifiedDerivative(Settings.singleVariableDefault), lowerBound, upperBound);
        if (criticalPoints.length == 0)
            return new double[0];

        List<Double> secondDerivative = new LinkedList<>();
        for (double criticalPoint : criticalPoints) {
            if (strategy.test(function.getNthDerivative(Settings.singleVariableDefault, 2).evaluate(Map.of(Settings.singleVariableDefault, criticalPoint)), 0.0))
                secondDerivative.add(criticalPoint);
        }
        return secondDerivative.stream().mapToDouble(i -> i).toArray();
    }

    private static double findSmallestOrLargest(GeneralFunction function, double[] numbers, BiPredicate<? super Double, ? super Double> strategy) {
        if (numbers.length == 1) {
            return numbers[0];
        }
        if (numbers.length == 0) {
            return Double.NaN;
        }
        double[] functionAtPoints = new double[numbers.length];
        for (int i = 0; i < functionAtPoints.length; i++) {
            functionAtPoints[i] = function.evaluate(Map.of(Settings.singleVariableDefault, numbers[i]));
        }

        int index = 0;
        for (int i = 1; i < functionAtPoints.length; i++) {
            if (functionAtPoints[i] == functionAtPoints[index]) {
                return Double.NaN;
            } else if (strategy.test(functionAtPoints[i], functionAtPoints[index])) {
                index = i;
            }
        }
        return numbers[index];
    }

}
