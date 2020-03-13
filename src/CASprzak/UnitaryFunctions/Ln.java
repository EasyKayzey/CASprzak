package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
public class Ln extends UnitaryFunction {
    public Ln(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "ln(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.log(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getDerivative(varID), new Reciprocal(function));
    }

    public Function clone() {
        return new Ln(function.clone());
    }

    public Function simplify() {
        return new Ln(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
