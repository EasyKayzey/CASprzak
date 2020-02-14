package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
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
        return Math.log(function.evaluate(variableValues));
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(function.derivative(varID), new Reciprocal(function));
    }
}
