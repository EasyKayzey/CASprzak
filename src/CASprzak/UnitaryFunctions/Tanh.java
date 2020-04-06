package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Tanh extends UnitaryFunction {
    public Tanh(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.tan(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(2), new Cosh(function)));
    }

    public Function clone() {
        return new Tanh(function.clone());
    }

    public Function simplifyInternal() {
        return new Tanh(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
