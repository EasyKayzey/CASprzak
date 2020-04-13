package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;

public class Sec extends UnitaryFunction {

    public Sec(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return 1 / Math.cos(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Tan(function), new Sec(function), function.getSimplifiedDerivative(varID));
    }

    public UnitaryFunction me(Function operand) {
        return new Sec(operand);
    }

}
