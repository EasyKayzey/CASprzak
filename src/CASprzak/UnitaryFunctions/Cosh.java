package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Cosh extends UnitaryFunction {
    public Cosh(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cosh(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
