package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Tanh extends UnitaryFunction {
    public Tanh(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tanh(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(function.derivative(varID), new Pow(new Constant(2), new Cosh(function)));
    }
}
