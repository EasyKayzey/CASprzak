package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Csc extends UnitaryFunction {
    public Csc(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Constant(-1), new Cot(function), new Csc(function), function.getSimplifiedDerivative(varID));
    }

    @Override
    public double evaluate(double... variableValues) {
        return 1 / Math.sin(function.evaluate(variableValues));
    }

    public UnitaryFunction me(Function operand) {
        return new Csc(operand);
    }

    public int compareTo( Function f) {
        return 0;
    }
}