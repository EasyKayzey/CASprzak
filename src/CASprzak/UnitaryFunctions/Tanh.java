package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Tanh extends UnitaryFunction {
    public Tanh(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tanh(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }
}
