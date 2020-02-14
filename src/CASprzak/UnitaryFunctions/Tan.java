package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import org.jetbrains.annotations.NotNull;

public class Tan extends UnitaryFunction {
    public Tan(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "tan(" + function.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Pow(new Constant(2), new Sec(function)), function.getDerivative(varID));
    }

    public Function clone() {
        return new Tan(function.clone());
    }

    public Function simplify() {
        return new Tan(function.simplify());
    }

    public int compareTo(@NotNull Function f) {
        return 0;
    }
}
