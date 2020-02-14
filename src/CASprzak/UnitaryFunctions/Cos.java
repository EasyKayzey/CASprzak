package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Cos extends UnitaryFunction {
    public Cos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cos(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.cos(function.evaluate(variableValues));
    }
}
