package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
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
    public Function getDerivative(int varID) {
        return new Multiply(new Multiply(new Tan(function), new Sec(function)), function.getDerivative(varID));
    }
}
