package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.UnitaryFunctions.Ln;

public class Logb extends BinaryFunction {
    public Logb(Function argument, Function base) {
        super(argument, base);
    }

    @Override
    public String toString() {
        return "log_{" + function2.toString() + "}(" + function1.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Add(new Multiply(function1.getSimplifiedDerivative(varID), new Ln(function2), new Pow(new Constant(-1), function1)), new Multiply(new Constant(-1), function2.getSimplifiedDerivative(varID), new Ln(function1), new Pow(new Constant(-1), function2))), new Pow(new Constant(-2), new Ln(function2)));
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
    }

    public Function clone() {
        return new Logb(function1.clone(), function2.clone());
    }

    public Function simplify() {
        return new Logb(function1.simplify(), function2.simplify());
    }

    public int compareTo( Function f) {
        return 0;
    }
}
