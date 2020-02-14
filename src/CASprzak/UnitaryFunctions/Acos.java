package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Acos extends UnitaryFunction {
    public Acos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "acos(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
