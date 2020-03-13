package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Sin extends UnitaryFunction {
    public Sin(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "sin(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.sin(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Cos(function), function.getDerivative(varID));
    }

    public Function clone() {
        return new Sin(function.clone());
    }

    public Function simplify() {
        return new Sin(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
