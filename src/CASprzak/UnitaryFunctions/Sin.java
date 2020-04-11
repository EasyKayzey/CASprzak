package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Sin extends UnitaryFunction {
    public Sin(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.sin(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Cos(function), function.getSimplifiedDerivative(varID));
    }

    public UnitaryFunction me(Function operand) {
        return new Sin(operand);
    }

}
