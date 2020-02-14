package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Ln extends UnitaryFunction {
    public Ln(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "ln(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
