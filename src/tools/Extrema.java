package tools;

import functions.Function;

import java.util.ArrayList;

public class Extrema {
    public static double findLocalMinima(Function function, double lowerBound, double upperBound) {
        double[] criticalPoints = SingleVariableSolver.getSolutionsRange(function.getDerivative(0), lowerBound, upperBound);
        if (criticalPoints.length == 0) return Double.NaN;

        ArrayList<Double> secondDerivativeIsPositive = new ArrayList<>();
        for (double criticalPoint: criticalPoints) {
            if (function.getNthDerivative(0,2).evaluate(criticalPoint) > 0) {
                secondDerivativeIsPositive.add(criticalPoint);
            }
        }
        if (secondDerivativeIsPositive.size() == 1) {
            return secondDerivativeIsPositive.get(0);
        }
        double[] functionAtPoints = new double[secondDerivativeIsPositive.size()];
        double smallest = functionAtPoints[0];
        for (int i = 1; i < functionAtPoints.length; i++) {
            if (functionAtPoints[i] == smallest) {
                return Double.NaN;
            } else if (functionAtPoints[i] < smallest) {
                smallest = functionAtPoints[i];
            }
        }
        return smallest;
    }
}
