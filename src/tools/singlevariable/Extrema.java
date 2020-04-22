package tools.singlevariable;

import core.Settings;
import functions.Function;

import java.util.ArrayList;
import java.util.List;

public class Extrema {
    //TODO make entire class use ListIterator

    private Extrema(){}

    /**
     * Returns the local minima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose minima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the local minima of function on the specified range
     */
    public static double findLocalMinima(Function function, double lowerBound, double upperBound) {
       double[] secondDerivativeIsPositive = findDerivativePoints(function, lowerBound, upperBound, 2, new GreaterThan());
        return findSmallestOrLargest(function, secondDerivativeIsPositive, new LessThan());
    }


    /**
     * Returns the local maxima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the local maxima of function on the specified range
     */
    public static double findLocalMaxima(Function function, double lowerBound, double upperBound) {
        double[] secondDerivativeIsNegative = findDerivativePoints(function, lowerBound, upperBound, 2, new LessThan());
        return findSmallestOrLargest(function, secondDerivativeIsNegative, new GreaterThan());
    }

    /**
     * Returns any minima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose minima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any minima of function on the specified range
     */
    public static double[] findAnyMinima(Function function, double lowerBound, double upperBound) {
        return findDerivativePoints(function, lowerBound, upperBound, 2, new GreaterThan());
    }

    /**
     * Returns any maxima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any maxima of function on the specified range
     */
    public static double[] findAnyMaxima(Function function, double lowerBound, double upperBound) {
       return findDerivativePoints(function, lowerBound, upperBound, 2, new LessThan());
    }

    /**
     * Returns any inflection point of a {@link Function} function on a specified range
     * @param function The {@link Function} whose inflection points are being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any inflection point of function on the specified range
     */
    public static double[] findAnyInflectionPoints(Function function, double lowerBound, double upperBound) {
        return findDerivativePoints(function, lowerBound, upperBound, 2, new InMargin());
    }

    private static double[] findDerivativePoints(Function function, double lowerBound, double upperBound, int differentiations, ComparisonStrategy strategy) {
        double[] points = Solver.getSolutionsRange(function.getDerivative(0), lowerBound, upperBound);
        if (points.length == 0) return null;

        List<Double> derivativePoints = new ArrayList<>();
        for (double point : points) {
            if (strategy.compare(function.getNthDerivative(0, 2).evaluate(point), 0)) {
                derivativePoints.add(point);
            }
        }
        return derivativePoints.stream().mapToDouble(i -> i).toArray();
    }

    private static double findSmallestOrLargest(Function function, double[] numbers, ComparisonStrategy strategy) {
        if (numbers.length == 1) {
            return numbers[0];
        }
        double[] functionAtPoints = new double[numbers.length];
        for (int i = 0; i < functionAtPoints.length; i++) {
            functionAtPoints[i] = function.evaluate(numbers[i]);
        }
        int index = 0;
        for (int i = 1; i < functionAtPoints.length; i++) {
            if (functionAtPoints[i] == functionAtPoints[index]) {
                return Double.NaN;
            } else if (strategy.compare(functionAtPoints[i], functionAtPoints[index])) {
                index = i;
            }
        }
        return numbers[index];
    }

    static abstract class ComparisonStrategy {
        abstract boolean compare(double a, double b);
    }

    static class LessThan extends ComparisonStrategy {
        boolean compare(double a, double b) {
            return a < b;
        }
    }

    static class GreaterThan extends ComparisonStrategy {
        boolean compare(double a, double b) {
            return a > b;
        }
    }

    static class InMargin extends ComparisonStrategy {
        boolean compare(double a, double b) {
            return (a - b < Settings.zeroMargin && b - a < Settings.zeroMargin);
        }
    }
}
