package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Asin extends UnitaryFunction {
    public Asin(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "asin(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.asin(function.evaluate(variableValues));
    }
}
