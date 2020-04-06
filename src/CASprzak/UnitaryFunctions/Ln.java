package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Ln extends UnitaryFunction {
    public Ln(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.log(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), function));
    }

    public Function clone() {
        return new Ln(function.clone());
    }

    public UnitaryFunction simplifyInternal() {
        return new Ln(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
