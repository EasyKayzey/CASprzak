package CASprzak.UnitaryFunctions;

import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import org.jetbrains.annotations.NotNull;

public class Cos extends UnitaryFunction {
    public Cos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cos(" + function.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        return  new Multiply(new Multiply(new Sin(function), new Constant(-1)), function.getDerivative(varID));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.cos(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Cos(function.clone());
    }

    public Function simplify() {
        return new Cos(function.simplify());
    }

    public int compareTo(@NotNull Function f) {
        return 0;
    }
}
