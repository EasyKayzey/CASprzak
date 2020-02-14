package CASprzak.BinaryFunctions;

import CASprzak.Function;

public class Logb extends BinaryFunction {
    public Logb(Function function1, Function function2) {
        super(function1, function2);
    }

    @Override
    public String toString() {
        return "log_(" + function2.toString() + ")(" + function1.toString() + ")";
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
    }
}
