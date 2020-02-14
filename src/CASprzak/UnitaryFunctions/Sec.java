package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
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
        return 1 / Math.cos(function.evaluate(variableValues));
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(new Multply(new Tan(function), new Sec(function)), function.derivative(varID));
    }
}
