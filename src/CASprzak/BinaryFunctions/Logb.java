package CASprzak.BinaryFunctions;

import CASprzak.CommutativeFunctions.Add;
import CASprzak.CommutativeFunctions.Multiply;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
import CASprzak.UnitaryFunctions.Acos;
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
    public Function getDerivative(int varID) {
        return new Multiply( new Add(new Multiply( new Multiply(function1.getDerivative(varID), new Ln(function2)), new Reciprocal(function1)), new Negative(new Multiply( new Multiply(function2.getDerivative(varID), new Ln(function1)), new Reciprocal(function2)))), new Reciprocal( new Pow(new Constant(2), new Ln( function2 ))));
    }

    @Override
    public double evaluate(double[] variableValues) {
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
