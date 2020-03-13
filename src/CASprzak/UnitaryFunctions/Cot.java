package CASprzak.UnitaryFunctions;

import CASprzak.BinaryFunctions.Pow;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Cot extends UnitaryFunction {
    public Cot(Function function) {
        super(function);
    }

    @Override
    public String toString() {
        return "cot(" + function.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        return  new Multiply(new Negative(new Pow(new Csc(function), new Constant(2))), function.getDerivative(varID));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return 1 / Math.tan(function.evaluate(variableValues));
    }

    public Function clone() {
        return new Cot(function.clone());
    }

    public Function simplify() {
        return new Cot(function.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
