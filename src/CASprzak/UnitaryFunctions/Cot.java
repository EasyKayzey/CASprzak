package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Cot extends UnitaryFunction {
    public Cot(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cot(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 0;
    }
}
