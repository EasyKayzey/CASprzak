package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Cos extends UnitaryFunction {
    public Cos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cos(" + function.toString() + ")";
    }

    @Override
    public Function derivative(tyjk) {
        return  new Multply(new Multply(new Sin(function), new Constant(-1)), function.derivative(tyjk));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.cos(function.evaluate(variableValues));
    }
}
