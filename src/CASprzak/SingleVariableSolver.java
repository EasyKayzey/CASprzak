package CASprzak;

import CASprzak.SpecialFunctions.Constant;

import java.util.List;

public class SingleVariableSolver {
    private double newtonsMethod(Function expression, double value) {
        return value - expression.evaluate(value) / expression.getSimplifiedDerivative(0).evaluate(value);
    }

    public double getSolutionPoint(Function expression, double initialPoint, int runs) {
        if (expression.evaluate(initialPoint) == 0) return initialPoint;
        if (expression instanceof Constant) return Double.NaN;
        for (int i = 0; i < runs; i++){
            initialPoint = newtonsMethod(expression, initialPoint);
            if (i % 25 == 0) {
                if (initialPoint < 1E-15 && initialPoint > -1E-15) return 0;
            }
        }
        if (expression.evaluate(initialPoint) < 1E-3 && expression.evaluate(initialPoint) > -1E-3) return initialPoint;
        return Double.NaN;
    }

    public double getSolutionPoint(Function expression, double initialPoint) {
        return getSolutionPoint(expression, initialPoint, 100);
    }

    public double[] getSolutionsRange(Function expression, double lower, double upper) {
        List<Double> solutions = ArrLib.createRange(upper, lower, 17);
        for (int j = 0; j < 1000; j++) {
            for (int i = 0; i < solutions.size(); i++) {
                solutions.set(i, newtonsMethod(expression, solutions.get(i)));
            }
            if (j % 25 == 0) {
                for (int i = 0; i < solutions.size(); i++) {
                    if (solutions.get(i) < 1E-15 && solutions.get(i) > -1E-15) solutions.set(i, 0.0);
                }
            }
        }
        for (int i = 0; i < solutions.size(); i++) {
            if (!(expression.evaluate(solutions.get(i)) < 1E-3 && expression.evaluate(solutions.get(i)) > -1E-3)) solutions.set(i, Double.NaN);
        }
        ArrLib.nanRemover(solutions);
        ArrLib.removeRepeatsInOrder(solutions);

        double[] solutionsArray = new double[solutions.size()];
        for (int i = 0; i < solutionsArray.length; i++)
            solutionsArray[i] = solutions.get(i);
        return solutionsArray;
    }
}
