package functions.unitary;

import functions.Function;

public class Sech extends UnitaryFunction {
    public Sech(Function function) {
        super(function);
    }

    @Override
    public Function getDerivative(int varID) {
        return null;
    }

    @Override
    public double evaluate(double... variableValues) {
        return 1/Math.cosh(function.evaluate(variableValues));
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Sech(operand);
    }
}
