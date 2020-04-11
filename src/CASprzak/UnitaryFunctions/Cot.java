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
    public Function getDerivative(int varID) {
        return  new Multiply(new Constant(-1), new Pow(new Constant(2), new Csc(function)), function.getSimplifiedDerivative(varID));
    }

    @Override
    public double evaluate(double... variableValues) {
        return 1 / Math.tan(function.evaluate(variableValues));
    }

    public UnitaryFunction me(Function operand) {
        return new Cot(operand);
    }

    public int compareTo( Function f) {
        return 0;
    }
}
