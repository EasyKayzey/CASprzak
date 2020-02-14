package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import org.jetbrains.annotations.NotNull;

public class Tanh extends UnitaryFunction {
    public Tanh(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tanh(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getDerivative(varID), new Pow(new Constant(2), new Cosh(function)));
    }

    public Function clone() {
        return new Tanh(function.clone());
    }

    public Function simplify() {
        return new Tanh(function.simplify());
    }

    public int compareTo(@NotNull Function f) {
        return 0;
    }
}
