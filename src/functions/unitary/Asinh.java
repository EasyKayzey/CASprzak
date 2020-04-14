package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Asinh extends UnitaryFunction {
    public Asinh(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-0.5), new Add(new Constant(1), new Pow(new Constant(2), function))));
    }

    @Override
    public double evaluate(double... variableValues) {
        double functionEvaluated = function.evaluate(variableValues);
        return Math.log(functionEvaluated+Math.sqrt(functionEvaluated*functionEvaluated+1));
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Asinh(operand);
    }
}
