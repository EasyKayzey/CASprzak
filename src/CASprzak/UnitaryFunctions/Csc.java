package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Csc extends UnitaryFunction {
    public Csc(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "csc(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}