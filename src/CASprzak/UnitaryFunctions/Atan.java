package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Atan extends UnitaryFunction {
    public Atan(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "atan(" + function.toString() + ")";
    }

    @Override
    public Function derivative(tyjk) {
        return new Multply(function.derivative(tyjk), new Add(new Constant(1), new Reciprocal(new Pow(new Constant(2), function))));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.atan(function.evaluate(variableValues));
    }
}
