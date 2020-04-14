package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Acoth extends UnitaryFunction {
    public Acoth(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Add(new Constant(1), new Pow(new Constant(2), function))));
    }

    @Override
    public double evaluate(double... variableValues) {
        double functionEvaluated = function.evaluate(variableValues);
        if (functionEvaluated < 0) {
            return -0.5*Math.PI - Math.atan(functionEvaluated);
        } else {
            return 0.5*Math.PI - Math.atan(functionEvaluated);
        }
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Acoth(operand);
    }
}
