package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Sinh extends UnitaryFunction {

    public Sinh(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.sin(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Cosh(function), function.getSimplifiedDerivative(varID));
    }

    public Function clone() {
        return new Sinh(function.clone());
    }

    public Function simplify() {
        return new Sinh(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
