package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
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
    public Function derivative(int varID) {
        return new Multply(new Sinh(function), function.derivative(varID));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.cosh(function.evaluate(variableValues));
    }
}
