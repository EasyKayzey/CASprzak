package CASprzak;

import java.util.ArrayList;

public class SingleVariableSolver {

    private double newtonsMethod(Function expression, double value) {
        return value - expression.evaluate(new double[]{value}) / expression.getDerivative(0).evaluate(new double[]{value});
    }

    public double getSolutionPoint(Function expression, double initialPoint) {
        if (expression.evaluate(new double[]{initialPoint}) == 0) return initialPoint;
        for (int i = 0; i < 500; i++){
            initialPoint = newtonsMethod(expression, initialPoint);
        }
        return initialPoint;
    }

    public double[] getSolutionsRange(Function expression, double a, double b) {
        ArrayList<Double> tempSolutions = new ArrayList<>();
        ArrayList<Double> finalSolutions = new ArrayList<>();
        //TODO
        return null;
    }
}
