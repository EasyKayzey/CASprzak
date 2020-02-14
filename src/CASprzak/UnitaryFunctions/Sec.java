package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Sec extends UnitaryFunction {
    public Sec(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "sec(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
