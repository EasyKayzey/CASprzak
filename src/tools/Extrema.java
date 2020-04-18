package tools;

import functions.Function;

import java.util.ArrayList;
import java.util.List;

public class Extrema {
    public static double findLocalMinima(Function function, double lowerBound, double upperBound) {
        double[] criticalPoints = SingleVariableSolver.getSolutionsRange(function.getDerivative(0), lowerBound, upperBound);
        if (criticalPoints.length == 0) return Double.NaN;

        List<Double> secondDerivativeIsPositive = new ArrayList<>();
        for (double criticalPoint : criticalPoints) {
            if (function.getNthDerivative(0, 2).evaluate(criticalPoint) > 0) {
                secondDerivativeIsPositive.add(criticalPoint);
            }
        }
        if (secondDerivativeIsPositive.size() == 1) {
            return secondDerivativeIsPositive.get(0);
        }
        double[] functionAtPoints = new double[secondDerivativeIsPositive.size()];
        for (int i = 0; i < functionAtPoints.length; i++) {
            functionAtPoints[i] = function.evaluate(secondDerivativeIsPositive.get(i));
        }
        int smallest = 0;
        for (int i = 1; i < functionAtPoints.length; i++) {
            if (functionAtPoints[i] == functionAtPoints[smallest]) {
                return Double.NaN;
            } else if (functionAtPoints[i] < functionAtPoints[smallest]) {
                smallest = i;
            }
        }
        return secondDerivativeIsPositive.get(smallest);
    }
}
