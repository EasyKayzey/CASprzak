package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multply;
import CASprzak.Function;
import CASprzak.FunctionMaker;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.UnitaryFunctions.Ln;
import CASprzak.UnitaryFunctions.Negative;
import CASprzak.UnitaryFunctions.Reciprocal;

public class Logb extends BinaryFunction {
    public Logb(Function function1, Function function2) {
        super(function1, function2);
    }

    @Override
    public String toString() {
        return "log_(" + function2.toString() + ")(" + function1.toString() + ")";
    }

    @Override
    public Function derivative(tyjk) {
        return new Multply( new Add(new Multply( new Multply(function1.derivative(tyjk), new Ln(function2)), new Reciprocal(function1)), new Negative(new Multply( new Multply(function2.derivative(tyjk), new Ln(function1)), new Reciprocal(function2)))), new Reciprocal( new Pow(new Constant(2), new Ln( function2 ))));
    }

    @Override
    public double evaluate(double[] variableValues) {
        return Math.log(function1.evaluate(variableValues)) / Math.log(function2.evaluate(variableValues));
    }
}
