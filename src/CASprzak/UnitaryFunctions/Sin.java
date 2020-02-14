package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;

public class Sin extends UnitaryFunction {
    public Sin(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "sin(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.sin(function.evaluate(variableValues));
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(new Cos(function), function.derivative(varID));
    }


}
