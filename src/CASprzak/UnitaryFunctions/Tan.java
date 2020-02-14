package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Tan extends UnitaryFunction {
    public Tan(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tan(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }

    @Override
    public Function derivative(int varID) {
        return new Multply(new Pow(new Constant(2), new Sec(function)), function.derivative(varID));
    }
}
