package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Acos extends UnitaryFunction {
    public Acos(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "acos(" + function.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Multiply(function.getDerivative(varID), new Constant(-1)), new Reciprocal(new Pow(new Constant(0.5), ( new Add(new Constant(1), new Negative(new Pow(new Constant(2), function)))))));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.acos(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Acos(function.clone());
    }

    public Function simplify() {
        return new Acos(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
