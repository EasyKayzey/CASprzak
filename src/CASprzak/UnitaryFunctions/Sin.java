package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Sin extends UnitaryFunction {
    public Sin(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "sin(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.sin(function.evaluate(variableValues));
    }
}
