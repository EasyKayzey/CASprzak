package CASprzak;

import java.util.ArrayList;
import java.util.LinkedList;

public class SingleVariableSolver {

    private double newtonsMethod(Function expression, double value) {
        return value - expression.evaluate(new double[]{value}) / expression.getDerivative(0).simplifyTimes(10).evaluate(new double[]{value});
        //TODO reproduce error
    }

    public double getSolutionPoint(Function expression, double initialPoint) {
        if (expression.evaluate(new double[]{initialPoint}) == 0) return initialPoint;
        for (int i = 0; i < 1000; i++){
            initialPoint = newtonsMethod(expression, initialPoint);
            if (i % 25 == 0) {
                if (initialPoint < 1E-15 && initialPoint > -1E-15) return 0;
            }
        }
        return initialPoint;
    }

    public double[] getSolutionsRange(Function expression, double lower, double upper) {
        LinkedList<Double> solutions = ArrLib.createRange(upper, lower, 17);
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
        ArrLib.nanRemover(solutions);
        ArrLib.removeRepeats(solutions);

        double[] solutionsArray = new double[solutions.size()];
        for (int i = 0; i < solutionsArray.length; i++){
            solutionsArray[i] = solutions.get(i);
        }
        return solutionsArray;
    }
}
