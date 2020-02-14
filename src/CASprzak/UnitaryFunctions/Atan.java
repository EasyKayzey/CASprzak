package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Atan extends UnitaryFunction {
    public Atan(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "atan(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
