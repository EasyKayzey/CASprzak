package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Acos extends UnitaryFunction {
    public Acos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "acos(" + function.toString() + ")";
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(new Multply(function.derivative(varID), new Constant(-1)), new Reciprocal(new Pow(new Constant(0.5), ( new Add(new Constant(1), new Negative(new Pow(new Constant(2), function)))))));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.acos(function.evaluate(variableValues));
    }
}
