package CASprzak.UnitaryFunctions;

import CASprzak.Function;

public class Tan extends UnitaryFunction {
    public Tan(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tan(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }
}
