package CASprzak;

import java.security.Signature;

public class SingleVariableSolver {

    private double newtonsMethod(Function expression, double value) {
        return value - expression.evaluate(new double[]{value}) / expression.getDerivative(0).evaluate(new double[]{value});
    }

    public double getSolutionPoint(Function expression, double initialPoint) {
        for (int i = 0; i < 100; i++){
            initialPoint = newtonsMethod(expression, initialPoint);
        }
        return initialPoint;
    }
}
