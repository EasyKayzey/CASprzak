package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Cot extends UnitaryFunction {
    public Cot(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cot(" + function.toString() + ")";
    }

    @Override
    public Function derivative(tyjk) {
        return  new Multply(new Negative(new Pow(new Csc(function), new Constant(2))), function.derivative(tyjk));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 1 / Math.tan(function.evaluate(variableValues));
    }
}
