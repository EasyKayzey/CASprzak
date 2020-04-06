package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Cosh extends UnitaryFunction {
    public Cosh(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Sinh(function), function.getSimplifiedDerivative(varID));
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.cosh(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Cosh(function.clone());
    }

    public UnitaryFunction simplifyInternal() {
        return new Cosh(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
