package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
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
    public Function getDerivative(int varID) {
        return new Multiply(new Pow(new Constant(2), new Sec(function)), function.getDerivative(varID));
    }
}
