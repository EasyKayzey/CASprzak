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
       double[] secondDerivativeIsPositive = findPoints(function, lowerBound, upperBound, 1);
        return findSmallestOrLargest(function, secondDerivativeIsPositive, -1);
    }


    /**
     * Returns the local maxima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return the local maxima of function on the specified range
     */
    public static double findLocalMaxima(Function function, double lowerBound, double upperBound) {
        double[] secondDerivativeIsNegative = findPoints(function, lowerBound, upperBound, -1);
        return findSmallestOrLargest(function, secondDerivativeIsNegative, 1);
    }

    /**
     * Returns any minima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose minima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any minima of function on the specified range
     */
    public static double[] findAnyMinima(Function function, double lowerBound, double upperBound) {
        return findPoints(function, lowerBound, upperBound, 1);
    }

    /**
     * Returns any maxima of a {@link Function} function on a specified range
     * @param function The {@link Function} whose maxima is being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any maxima of function on the specified range
     */
    public static double[] findAnyMaxima(Function function, double lowerBound, double upperBound) {
       return findPoints(function, lowerBound, upperBound, -1);
    }

    /**
     * Returns any inflection point of a {@link Function} function on a specified range
     * @param function The {@link Function} whose inflection points are being found
     * @param lowerBound The lower bound of the range
     * @param upperBound The upper bound of the range
     * @return any inflection point of function on the specified range
     */
    public static double[] findAnyInflectionPoints(Function function, double lowerBound, double upperBound) {
        double[] criticalPoints = Solver.getSolutionsRange(function.getDerivative(0), lowerBound, upperBound);
        if (criticalPoints.length == 0) return null;

        List<Double> secondDerivativeIsZero = new ArrayList<>();
        for (double criticalPoint : criticalPoints) {
            if (Math.abs(function.getNthDerivative(0, 2).evaluate(criticalPoint)) < Settings.zeroMargin) {
                secondDerivativeIsZero.add(criticalPoint);
            }
        }
        return secondDerivativeIsZero.stream().mapToDouble(i -> i).toArray();

    }

    private static double[] findPoints(Function function, double lowerBound, double upperBound, double sign) {
        double[] criticalPoints = Solver.getSolutionsRange(function.getDerivative(0), lowerBound, upperBound);
        if (criticalPoints.length == 0) return null;

        List<Double> secondDerivative = new ArrayList<>();
        for (double criticalPoint : criticalPoints) {
            if (function.getNthDerivative(0, 2).evaluate(criticalPoint)*sign > 0) {
                secondDerivative.add(criticalPoint);
            }
        }
        return secondDerivative.stream().mapToDouble(i -> i).toArray();
    }

    private static double findSmallestOrLargest(Function function, double[] numbers, double sign) {
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
            } else if (functionAtPoints[i]*sign > functionAtPoints[index]*sign) {
                index = i;
            }
        }
        return numbers[index];
    }
}
