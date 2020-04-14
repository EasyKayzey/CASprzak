package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Tanh extends UnitaryFunction {
    public Tanh(Function function) {
        super(function);
    }

    @Override
    public double evaluate(double... variableValues) {
        return Math.tanh(function.evaluate(variableValues));
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(function.getSimplifiedDerivative(varID), new Pow(new Constant(-2), new Cosh(function)));
    }

    public UnitaryFunction me(Function operand) {
        return new Tanh(operand);
    }

}
