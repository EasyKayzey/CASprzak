package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Csc extends UnitaryFunction {
    public Csc(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "csc(" + function.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Negative(new Multiply(new Cot(function), new Csc(function))), function.getDerivative(varID));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 1 / Math.sin(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Csc(function.clone());
    }

    public Function simplify() {
        return new Csc(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}