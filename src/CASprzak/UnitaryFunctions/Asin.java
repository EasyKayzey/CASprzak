package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Asin extends UnitaryFunction {
    public Asin(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "asin(" + function.toString() + ")";
    }

    @Override
    public Function derivative(tyjk) {
        return new Multply(function.derivative(tyjk), new Reciprocal(new Pow(new Constant(0.5), ( new Add(new Pow(new Constant(2), function), new Negative(new Constant(1))))));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.asin(function.evaluate(variableValues));
    }
}
