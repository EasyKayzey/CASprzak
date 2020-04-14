package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Abs extends UnitaryFunction {
    public Abs(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.abs(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return null;
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Abs(operand);
    }
}
