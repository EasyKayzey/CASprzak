package CASprzak.BinaryFunctions;

import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

public class Logb extends BinaryFunction {
    public Logb(Function function1, Function function2) {
        super(function1, function2);
    }

    @Override
    public String toString() {
        return "log_{" + function2.toString() + "}(" + function1.toString() + ")";
    }

    @Override
    public Function getDerivative(int varID) {
        //TODO
        System.out.println("PLS IMPLEMENT");
        return new Constant(0);
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
